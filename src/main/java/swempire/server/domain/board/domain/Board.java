package swempire.server.domain.board.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import swempire.server.domain.member.domain.Member;
import swempire.server.global.utils.TimeTracker;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class Board extends TimeTracker {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "board_id")
    private Long id;

    private String title;

    private String body;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    @JoinColumn(name = "member_id")
    Member member;

    @Builder
    public Board(String title, String body, Member member) {
        this.title = title;
        this.body = body;
        this.member = member;
    }

    public static Board of(String title, String body, Member member) {
        return Board.builder()
                .title(title)
                .body(body)
                .member(member)
                .build();
    }

    public void updateBoard(String title, String body) {
        this.title = title;
        this.body = body;
    }
}
