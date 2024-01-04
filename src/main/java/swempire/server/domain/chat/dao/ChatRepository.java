package swempire.server.domain.chat.dao;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import swempire.server.domain.board.domain.Board;
import swempire.server.domain.chat.domain.Chat;
import swempire.server.domain.chat.dto.ChatResponse;
import swempire.server.domain.member.domain.Member;

import java.util.List;
import java.util.Optional;

@Repository
public interface ChatRepository extends JpaRepository<Chat, Long> {

    @Query("SELECT new swempire.server.domain.chat.dto.ChatResponse(c.id, c.body, c.parentChatId, c.sequence, c.member.name, c.createdAt) " +
            "FROM Chat c " +
            "WHERE c.board.id = :boardIndex")
    List<ChatResponse> getAllChatsByBoardIndex(@Param("boardIndex") Long boardIndex);

    @Query("SELECT b FROM Board b WHERE b.id = :boardIndex")
    Optional<Board> findByBoardId(Long boardIndex);

    @Query("SELECT m FROM Member m WHERE m.email = :email")
    Optional<Member> findMemberByEmail(String email);

    @Query("SELECT c FROM Chat c WHERE c.parentChatId = :parentChatId ORDER BY c.sequence DESC LIMIT 1")
    Optional<Chat> getLastChatById(@Param("parentChatId") Long parentChatId);

    @Query("SELECT COUNT(c) FROM Chat c WHERE c.board.id = :boardIndex")
    Long findChatCountByBoardId(@Param("boardIndex") Long boardIndex);
}
