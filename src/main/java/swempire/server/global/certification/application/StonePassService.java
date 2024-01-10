package swempire.server.global.certification.application;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import swempire.server.domain.member.application.MemberService;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

@Slf4j
@RequiredArgsConstructor
@Transactional
@Service
public class StonePassService {

    private final MemberService memberService;
    private static final String OID_KEY = "hsw";
    private static final String SID = "hsw";

    @Transactional(readOnly = true)
    public String get2FactorToken(String email) {
        try {
            JsonObject requestBody = new JsonObject();
            requestBody.addProperty("OID_KEY", OID_KEY);
            requestBody.addProperty("SID", SID);
            requestBody.addProperty("USER_ID", email);

            String jsonInputString = requestBody.toString();

            URL url = new URL("https://dev.swempire.co.kr/app/svcif/easyauth/req");
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("POST");
            connection.setRequestProperty("Content-Type", "application/json");
            connection.setDoOutput(true);

            try (OutputStream os = connection.getOutputStream()) {
                byte[] input = jsonInputString.getBytes("utf-8");
                os.write(input, 0, input.length);
            }

            try (BufferedReader br = new BufferedReader(new InputStreamReader(connection.getInputStream(), "utf-8"))) {
                StringBuilder response = new StringBuilder();
                String responseLine;
                while ((responseLine = br.readLine()) != null) {
                    response.append(responseLine.trim());
                }

                log.info("전체 응답값: {}", response.toString());
                JsonObject jsonResponse = JsonParser.parseString(response.toString()).getAsJsonObject();
                String code = jsonResponse.getAsJsonPrimitive("RESULT_CODE").getAsString();

                if (code.equals("0")) {
                    return jsonResponse.getAsJsonPrimitive("ACCESS_TOKEN").getAsString();
                } else {
                    log.info("send request error");
                    return "error";
                }
            }
        } catch (IOException e) {
            throw new IllegalStateException("Error");
        }
    }

    @Transactional(readOnly = true)
    public void pushMobile(String twoFactorToken) {
        try {
            JsonObject requestBody = new JsonObject();
            requestBody.addProperty("ACCESS_TOKEN", twoFactorToken);

            String jsonInputString = requestBody.toString();

            URL url = new URL("https://dev.swempire.co.kr/app/svcif/push/send");
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("POST");
            connection.setRequestProperty("Content-Type", "application/json");
            connection.setDoOutput(true);

            try (OutputStream os = connection.getOutputStream()) {
                byte[] input = jsonInputString.getBytes("utf-8");
                os.write(input, 0, input.length);
            }

            try (BufferedReader br = new BufferedReader(new InputStreamReader(connection.getInputStream(), "utf-8"))) {
                StringBuilder response = new StringBuilder();
                String responseLine;
                while ((responseLine = br.readLine()) != null) {
                    response.append(responseLine.trim());
                }

                log.info("전체 응답값: {}", response.toString());
                JsonObject jsonResponse = JsonParser.parseString(response.toString()).getAsJsonObject();
                String code = jsonResponse.getAsJsonPrimitive("RESULT_CODE").getAsString();

                if (code.equals("0")) {
                    log.info("send success");
                } else {
                    log.info("send request error");
                }
            }
        } catch (IOException e) {
            log.error("IOException occurred: {}", e.getMessage());
            throw new IllegalStateException("Error");
        }
    }

    public String twoFactorComplete(String twoFactorToken, String email) {
        try {
            JsonObject requestBody = new JsonObject();
            requestBody.addProperty("ACCESS_TOKEN", twoFactorToken);

            String jsonInputString = requestBody.toString();

            URL url = new URL("https://dev.swempire.co.kr/app/svcif/easyauth/check");
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("POST");
            connection.setRequestProperty("Content-Type", "application/json");
            connection.setDoOutput(true);

            try (OutputStream os = connection.getOutputStream()) {
                byte[] input = jsonInputString.getBytes("utf-8");
                os.write(input, 0, input.length);
            }

            try (BufferedReader br = new BufferedReader(new InputStreamReader(connection.getInputStream(), "utf-8"))) {
                StringBuilder response = new StringBuilder();
                String responseLine;
                while ((responseLine = br.readLine()) != null) {
                    response.append(responseLine.trim());
                }

                log.info("전체 응답값: {}", response.toString());
                JsonObject jsonResponse = JsonParser.parseString(response.toString()).getAsJsonObject();
                String code = jsonResponse.getAsJsonPrimitive("RESULT_CODE").getAsString();

                if (code.equals("007")) {
                    log.info("mobile OTP called");
                    return "mobileOTP";
                }

                if (code.equals("0")) {
                    log.info("2Factor Success");
                    memberService.twoFactorSuccess(email);
                    return "main";
                } else {
                    log.info("2Factor Failed");
                    throw new IllegalStateException("statement error");
                }
            }
        } catch (IOException e) {
            throw new IllegalStateException("Error");
        }
    }

    public String mobileOTP(String twoFactorToken, String otp, String email) {
        try {
            JsonObject requestBody = new JsonObject();
            log.info("ACCESS_TOKEN : {}", twoFactorToken);
            log.info("USERPW : {}", otp);
            log.info("OTPTYPE : {}", "1");
            requestBody.addProperty("ACCESS_TOKEN", twoFactorToken);
            requestBody.addProperty("USERPW", otp);
            requestBody.addProperty("OTPTYPE", "1");

            String jsonInputString = requestBody.toString();
            log.info("jsonInputString = {}", jsonInputString);

            URL url = new URL("https://dev.swempire.co.kr/app/svcif/motp/check");
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("POST");
            connection.setRequestProperty("Content-Type", "application/json");
            connection.setDoOutput(true);

            try (OutputStream os = connection.getOutputStream()) {
                byte[] input = jsonInputString.getBytes("utf-8");
                os.write(input, 0, input.length);
            }

            try (BufferedReader br = new BufferedReader(new InputStreamReader(connection.getInputStream(), "utf-8"))) {
                StringBuilder response = new StringBuilder();
                String responseLine;
                while ((responseLine = br.readLine()) != null) {
                    response.append(responseLine.trim());
                }

                log.info("전체 응답값: {}", response.toString());
                JsonObject jsonResponse = JsonParser.parseString(response.toString()).getAsJsonObject();
                String code = jsonResponse.getAsJsonPrimitive("RESULT_CODE").getAsString();

                if (code.equals("0")) {
                    log.info("2Factor Success");
                    memberService.twoFactorSuccess(email);
                    return "main";
                } else {
                    log.info("2Factor Failed");
                    throw new IllegalStateException("statement error");
                }
            }
        } catch (IOException e) {
            throw new IllegalStateException("Error");
        }
    }
}
