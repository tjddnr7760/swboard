package swempire.server.domain.member.api;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import swempire.server.domain.member.application.MemberService;

@Slf4j
@RequestMapping("/member")
@RequiredArgsConstructor
@Controller
public class MemberController {

    private final MemberService memberService;

    @GetMapping("/login")
    public String getLoginPage() {
        return "";
    }

    @PostMapping("/login")
    public String authLogin() {
        return "";
    }

    @PostMapping("/logout")
    public String authLogout() {
        return "";
    }

    @GetMapping
    public String getSignUpPage() {
        return "";
    }

    @PostMapping
    public String postSignup() {
        return "";
    }
}
