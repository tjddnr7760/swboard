package swempire.server.domain.chat.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import swempire.server.domain.board.domain.Board;
import swempire.server.domain.member.domain.Member;
import swempire.server.global.utils.TimeTracker;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class Chat extends TimeTracker {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "chat_id")
    private Long id;

    private String body;

    private int section;

    private Long parentChatId;

    private int sequence;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "board_id")
    Board board;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    Member member;

    @Builder
    public Chat(String body, int section, Long parentChatId, int sequence, Board board, Member member) {
        this.body = body;
        this.section = section;
        this.parentChatId = parentChatId;
        this.sequence = sequence;
        this.board = board;
        this.member = member;
    }

    public static Chat defaultOf(Chat chat, String body, Long parentChatId, Board board, Member member) {
        return Chat.builder()
                .body(body)
                .section(chat.getSection() + 1)
                .parentChatId(parentChatId)
                .sequence(chat.getSequence() + 1)
                .board(board)
                .member(member)
                .build();
    }

    public static Chat initOf(String body, Board board, Member member) {
        return Chat.builder()
                .body(body)
                .section(0)
                .parentChatId(0L)
                .sequence(1)
                .board(board)
                .member(member)
                .build();
    }

    public static Chat parentChatIdZero(Chat chat, String body, Board board, Member member) {
        return Chat.builder()
                .body(body)
                .section(chat.getSection() + 1)
                .parentChatId(0L)
                .sequence(chat.getSequence() + 1)
                .board(board)
                .member(member)
                .build();
    }

    public void updateBody(String body) {
        this.body = body;
    }
}
