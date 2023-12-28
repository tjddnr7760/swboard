package swempire.server.domain.board.dto;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class BoardBodyResponse {

    private final Long id;

    private final String title;

    private final LocalDateTime createdAt;

    private final String name;

    private final String body;

    @Builder
    public BoardBodyResponse(Long id, String title, LocalDateTime createdAt, String name, String body) {
        this.id = id;
        this.title = title;
        this.createdAt = createdAt;
        this.name = name;
        this.body = body;
    }
}
