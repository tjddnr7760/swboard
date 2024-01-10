package swempire.server.domain.board.Api;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import swempire.server.domain.board.application.BoardService;
import swempire.server.domain.board.dto.BoardBodyResponse;
import swempire.server.domain.board.dto.BoardResponse;
import swempire.server.domain.board.dto.PostBoardRequest;
import swempire.server.domain.chat.application.ChatService;
import swempire.server.domain.chat.dto.ChatResponse;
import swempire.server.global.auth.memberDetails.MemberContextInform;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@RequestMapping("/board")
@RequiredArgsConstructor
@Controller
public class BoardController {

    private final BoardService boardService;
    private final ChatService chatService;

    @GetMapping("/main/{page}")
    public String getMainPage(Model model, Authentication authentication) {
        if (authentication != null && authentication.isAuthenticated()) {
            MemberContextInform member = (MemberContextInform) authentication.getPrincipal();
            Map<String, Object> userInfo = new HashMap<>();
            userInfo.put("email", member.getEmail());
            userInfo.put("name", member.getName());
            model.addAttribute("userInfo", userInfo);
        } else {
            model.addAttribute("userInfo", null);
        }
        List<BoardResponse> allBoards = boardService.getAllBoards();
        model.addAttribute("boards", allBoards);

        log.info("main page call");
        return "main";
    }

    @GetMapping
    public String getBoardPostPage() {
        return "addForm";
    }

    @PostMapping
    public String postBoard(@RequestBody PostBoardRequest postBoardRequest, Model model, Authentication authentication) {
        boardService.postSave(getEmail(authentication), postBoardRequest.getTitle(), postBoardRequest.getBody());

        if (authentication.isAuthenticated()) {
            MemberContextInform member = (MemberContextInform) authentication.getPrincipal();
            Map<String, Object> userInfo = new HashMap<>();
            userInfo.put("email", member.getEmail());
            userInfo.put("name", member.getName());
            model.addAttribute("userInfo", userInfo);

            List<BoardResponse> allBoards = boardService.getAllBoards();
            model.addAttribute("boards", allBoards);
        } else {
            model.addAttribute("userInfo", null);
        }
        return "main";
    }

    @GetMapping("/{Board_index}")
    public String getBoardSpecificPage(@PathVariable Long Board_index, Model model, Authentication authentication) {
        BoardBodyResponse boardBodyResponse = boardService.getSpecificBoard(Board_index);
        model.addAttribute("boardId", boardBodyResponse.getId());
        model.addAttribute("boardTitle", boardBodyResponse.getTitle());
        model.addAttribute("boardAuthor", boardBodyResponse.getName());
        model.addAttribute("boardCreatedAt", boardBodyResponse.getCreatedAt());
        model.addAttribute("boardBody", boardBodyResponse.getBody());

        List<ChatResponse> chatResponses = chatService.getAllChatsInOneBoard(Board_index);
        model.addAttribute("comments", chatResponses);

        try {
            String name = getName(authentication);
            model.addAttribute("currentUsername", name);
            log.info("{}", name);
        } catch (Exception e) {
            log.info("This chat is not yours");
        }
        return "boardResponse";
    }

    @GetMapping("/edit/{Board_index}")
    public String getBoardEditPage(@PathVariable Long Board_index, Model model) {
        BoardBodyResponse boardBodyResponse = boardService.getSpecificBoard(Board_index);
        model.addAttribute("boardId", boardBodyResponse.getId());
        model.addAttribute("boardTitle", boardBodyResponse.getTitle());
        model.addAttribute("boardAuthor", boardBodyResponse.getName());
        model.addAttribute("boardCreatedAt", boardBodyResponse.getCreatedAt());
        model.addAttribute("boardBody", boardBodyResponse.getBody());

        return "boardResponseEdit";
    }

    @PatchMapping("/edit/{Board_index}")
    public String patchEdit(@PathVariable("Board_index") Long boardIndex, @RequestBody PostBoardRequest postBoardRequest, Authentication authentication, Model model) {
        boardService.postEditSave(boardIndex, getEmail(authentication), postBoardRequest.getTitle(), postBoardRequest.getBody());
        return "boardResponse";
    }

    @DeleteMapping("/{Board_index}")
    public String deleteBoard(@PathVariable("Board_index") Long boardIndex, Authentication authentication) {
        log.info("Delete Board");
        boardService.boardDelete(boardIndex, getEmail(authentication));

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

    private String getName(Authentication authentication) {
        String name;
        if (authentication == null) {
            throw new IllegalStateException();
        } else {
            MemberContextInform memberInform = (MemberContextInform) authentication.getPrincipal();
            name = memberInform.getName();
        }
        return name;
    }
}
