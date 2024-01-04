package swempire.server.domain.chat.api;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import swempire.server.domain.chat.application.ChatService;
import swempire.server.domain.chat.dto.ChatRequest;
import swempire.server.global.auth.memberDetails.MemberContextInform;

@Slf4j
@RequestMapping("/chat")
@RequiredArgsConstructor
@Controller
public class ChatController {

    private final ChatService chatService;

    @PostMapping("/{Board_index}")
    public String postChat(@PathVariable("Board_index") Long boardIndex,
                           @RequestBody ChatRequest chatRequest,
                           Authentication authentication) {
        log.info("chat body : {}", chatRequest.getBody());
        chatService.saveChat(boardIndex, chatRequest.getBody(),
                getEmail(authentication), chatRequest.getParentChatId());
        return "boardResponse";
    }

    @PatchMapping("/{commentId}")
    public String patchChat(@PathVariable("commentId") Long commentId,
                            @RequestBody ChatRequest chatRequest,
                            Authentication authentication) {
        chatService.updateChat(commentId, chatRequest.getBody(), getEmail(authentication));
        log.info("chat update done");

        return "boardResponse";
    }

    @DeleteMapping("/{commentId}")
    public String deleteChat(@PathVariable("commentId") Long commentId, Authentication authentication) {
        chatService.deleteChat(commentId, getEmail(authentication));
        log.info("Delete success");
        return "boardResponse";
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
