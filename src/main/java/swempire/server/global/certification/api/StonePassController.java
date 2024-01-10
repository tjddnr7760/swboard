package swempire.server.global.certification.api;

import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import swempire.server.global.auth.memberDetails.MemberContextInform;
import swempire.server.global.certification.application.StonePassService;

@Slf4j
@RequestMapping("/stonepass")
@RequiredArgsConstructor
@Controller
public class StonePassController {

    private final StonePassService stonePassService;

    @GetMapping("/2Factor")
    public String get2FactorPage() {
        log.info("2FactorPage is called");
        return "stonepass/2Factor";
    }

    @GetMapping("/mobileOTP")
    public String getOptPage() {
        log.info("OTP 2Factor called");
        return "stonepass/mobileOTP";
    }

    @PostMapping("/mobileOTP")
    public void optVerification(@RequestHeader("twoFactorToken") String twoFactorToken,
                                  @RequestHeader("otpNum") String otpNum,
                                  Authentication authentication) {
        log.info("OTP 2Factor called");
        stonePassService.mobileOTP(twoFactorToken, otpNum, getEmail(authentication));
    }

    @PostMapping("/2Factor")
    public void twoFactorVerification(Authentication authentication, HttpServletResponse response) {
        log.info("2Factor start");
        log.info("email : {}", getEmail(authentication));

        String twoFactorToken = stonePassService.get2FactorToken(getEmail(authentication));
        log.info("twoFactorToken : {}", twoFactorToken);
        stonePassService.pushMobile(twoFactorToken);

        response.setHeader("twoFactorToken", twoFactorToken);
    }

    @PostMapping("/2FactorComplete")
    public void twoFactorComplete(@RequestHeader("twoFactorToken") String twoFactorToken,
                                    HttpServletResponse response,
                                    Authentication authentication) {

        log.info("twoFactorToken : {}", twoFactorToken);
        String redirect = stonePassService.twoFactorComplete(twoFactorToken, getEmail(authentication));
        response.setHeader("redirectPage", redirect);
        log.info("redirect page : {}", redirect);
        log.info("2Factor Completed");
    }

    private String getEmail(Authentication authentication) {
        String email;
        if (authentication == null) {
            throw new IllegalStateException();
        } else {
            MemberContextInform memberInform = (MemberContextInform) authentication.getPrincipal();
            email = memberInform.getEmail();
        }
        return email;
    }
}
