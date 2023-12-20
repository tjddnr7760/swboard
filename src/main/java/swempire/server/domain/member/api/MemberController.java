package swempire.server.domain.member.api;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import swempire.server.domain.member.application.MemberService;
import swempire.server.domain.member.dto.SignUpRequest;

@Slf4j
@RequestMapping("/member")
@RequiredArgsConstructor
@Controller
public class MemberController {

    private final MemberService memberService;

    @GetMapping("/login")
    public String getLoginPage() {
        log.info("login is called");
        return "login2";
    }

    @PostMapping("/logout")
    public String authLogout() {
        return "";
    }

    @GetMapping
    public String getSignUpPage() {
        return "signup";
    }

    @PostMapping
    public String postSignup(@ModelAttribute SignUpRequest signUpRequest, Model model) {
        boolean result = memberService.signUpMember(signUpRequest);
        model.addAttribute("signupSuccess", result);
        return "main";
    }
}
