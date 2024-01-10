package swempire.server.domain.board.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
public class PostBoardRequest {

    private final String title;
    private final String body;

    @Builder
    public PostBoardRequest(String title, String body) {
        this.title = title;
        this.body = body;
    }
}
