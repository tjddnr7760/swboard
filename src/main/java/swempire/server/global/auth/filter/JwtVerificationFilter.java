package swempire.server.global.auth.filter;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;
import swempire.server.global.auth.jwt.JwtTokenizer;
import swempire.server.global.auth.memberDetails.MemberContextInform;

import java.io.IOException;
import java.util.Map;

@Slf4j
@RequiredArgsConstructor
public class JwtVerificationFilter extends OncePerRequestFilter {

    private final JwtTokenizer jwtTokenizer;

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {

        if (request.getRequestURI().startsWith("/css/bootstrap.min.css.map")) {
            return;
        }

        try {
            log.info("verification filter start");

            Map<String, Object> claims = verifyJws(request).getBody();
            for (Map.Entry<String, Object> entry : claims.entrySet()) {
                String key = entry.getKey();
                Object value = entry.getValue();
                log.info("Claim Key: {}, Claim Value: {}", key, value);
            }
            setAuthenticationToContext(claims);
        } catch (Exception e) {
            log.error("normal user");
        }

        filterChain.doFilter(request, response);
    }

    private Jws<Claims> verifyJws(HttpServletRequest request) {
        String accessToken = request.getHeader("Authorization").replace("Bearer ", "");
        String base64EncodedSecretKey = jwtTokenizer.encodeBase64SecretKey(jwtTokenizer.getSecretKey());
        log.info("token extract success");

        return jwtTokenizer.getClaims(accessToken, base64EncodedSecretKey);
    }

    private void setAuthenticationToContext(Map<String, Object> claims) {
        String email = (String) claims.get("email");
        String name = (String) claims.get("name");

        MemberContextInform memberContextInform = MemberContextInform.builder()
                .email(email)
                .name(name)
                .build();

        UsernamePasswordAuthenticationToken authentication
                = new UsernamePasswordAuthenticationToken(memberContextInform, null, null);
        SecurityContextHolder.getContext().setAuthentication(authentication);
    }
}
