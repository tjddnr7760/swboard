package swempire.server.domain.chat.api;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import swempire.server.domain.chat.application.ChatService;

@Slf4j
@RequestMapping("/chat")
@RequiredArgsConstructor
@Controller
public class ChatController {

    private final ChatService chatService;

    @PostMapping("/{Board_index}")
    public String postChat() {
        return "";
    }

    @PatchMapping("/{Board_index}")
    public String patchChat() {
        return "";
    }

    @DeleteMapping("/{Board_index}")
    public String deleteChat() {
        return "";
    }
}
