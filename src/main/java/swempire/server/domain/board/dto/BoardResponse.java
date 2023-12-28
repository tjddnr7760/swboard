package swempire.server.domain.board.dto;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class BoardResponse {

    private final Long id;

    private final String title;

    private final LocalDateTime createdAt;

    private final String name;

    @Builder
    public BoardResponse(Long id, String title, LocalDateTime createdAt, String name) {
        this.id = id;
        this.title = title;
        this.createdAt = createdAt;
        this.name = name;
    }
}
