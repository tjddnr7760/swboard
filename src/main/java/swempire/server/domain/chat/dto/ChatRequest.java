package swempire.server.domain.chat.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
public class ChatRequest {

    private String body;
    private Long parentChatId;

    public ChatRequest() {}

    @Builder
    public ChatRequest(String body, Long parentChatId) {
        this.body = body;
        this.parentChatId = parentChatId;
    }
}
