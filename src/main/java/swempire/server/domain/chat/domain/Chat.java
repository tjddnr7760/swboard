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

    private Long parentChatId;

    private int sequence;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "board_id")
    Board board;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    Member member;

    @Builder
    public Chat(String body, Long parentChatId, int sequence, Board board, Member member) {
        this.body = body;
        this.parentChatId = parentChatId;
        this.sequence = sequence;
        this.board = board;
        this.member = member;
    }

    public static Chat defaultOf(Chat chat, String body, Long parentChatId, Board board, Member member) {
        return Chat.builder()
                .body(body)
                .parentChatId(parentChatId)
                .sequence(chat.getSequence() + 1)
                .board(board)
                .member(member)
                .build();
    }

    public static Chat initOf(String body, Board board, Member member) {
        return Chat.builder()
                .body(body)
                .parentChatId(0L)
                .sequence(0)
                .board(board)
                .member(member)
                .build();
    }

    public static Chat initReplyOf(Long parentChatId, String body, Board board, Member member) {
        return Chat.builder()
                .body(body)
                .parentChatId(parentChatId)
                .sequence(0)
                .board(board)
                .member(member)
                .build();
    }

    public static Chat parentChatIdZeroOf(Chat chat, String body, Board board, Member member) {
        return Chat.builder()
                .body(body)
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
