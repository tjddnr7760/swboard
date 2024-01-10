package swempire.server.domain.member.api;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import swempire.server.domain.member.application.MemberService;
import swempire.server.domain.member.dto.SignUpRequest;
import swempire.server.global.auth.memberDetails.MemberContextInform;

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

    @GetMapping("/logout")
    public String logout(Authentication authentication) {
        return "main";
    }

    @PostMapping("/logout")
    public String authLogout(Authentication authentication) {
        memberService.logout2FactorDelete(getEmail(authentication));
        return "main";
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
