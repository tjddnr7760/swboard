package swempire.server.domain.chat.dto;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class ChatResponse {

    private final Long id;

    private final String body;

    private final Long parentChatId;

    private final int sequence;

    private final String name;

    private final LocalDateTime createdAt;

    @Builder
    public ChatResponse(Long id, String body, Long parentChatId, int sequence, String name, LocalDateTime createdAt) {
        this.id = id;
        this.body = body;
        this.parentChatId = parentChatId;
        this.sequence = sequence;
        this.name = name;
        this.createdAt = createdAt;
    }
}
