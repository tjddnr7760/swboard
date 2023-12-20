package swempire.server.domain.board.Api;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import swempire.server.domain.board.application.BoardService;
import swempire.server.domain.member.domain.Member;
import swempire.server.global.auth.memberDetails.MemberContextInform;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@RequestMapping("/board")
@RequiredArgsConstructor
@Controller
public class BoardController {

    private final BoardService boardService;

    @GetMapping("/main/{page}")
    public String getMainPage(Model model, Authentication authentication) {
        if (authentication != null && authentication.isAuthenticated()) {
            MemberContextInform member = (MemberContextInform) authentication.getPrincipal();
            String email = member.getEmail();
            String name = member.getName();
            Map<String, Object> userInfo = new HashMap<>();
            userInfo.put("email", email);
            userInfo.put("name", name);
            model.addAttribute("userInfo", userInfo);

        } else {
            model.addAttribute("userInfo", null);
        }
        log.info("main page call");
        return "main";
    }

    @GetMapping
    public String getBoardPostPage() {
        return "addForm";
    }

    @PostMapping("/{Board_index}")
    public String postBoard() {
        return "";
    }

    @GetMapping("/{Board_index}")
    public String getBoardSpecificPage() {
        return "";
    }

    @GetMapping("/edit/{Board_index}")
    public String getBoardEditPage() {
        return "";
    }

    @PatchMapping("/edit/{Board_index}")
    public String patchEdit() {
        return "";
    }

    @DeleteMapping("/{Board_index}")
    public String deleteBoard() {
        return "";
    }
}
