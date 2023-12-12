package swempire.server.domain.board.Api;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import swempire.server.domain.board.application.BoardService;

@Slf4j
@RequestMapping("/board")
@RequiredArgsConstructor
@Controller
public class BoardController {

    private final BoardService boardService;

    @GetMapping("/{page}")
    public String getMainPage() {
        return "";
    }

    @GetMapping
    public String getBoardPostPage() {
        return " ";
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
