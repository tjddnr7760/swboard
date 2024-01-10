package swempire.server.domain.board.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import swempire.server.domain.board.domain.Board;
import swempire.server.domain.member.domain.Member;

import java.util.List;

@Repository
public interface BoardRepository extends JpaRepository<Board, Long> {

    @Query("SELECT m FROM Member m WHERE m.email = :email")
    Member findMemberByEmail(@Param("email") String email);

    @Query("SELECT b FROM Board b JOIN FETCH b.member")
    List<Board> findAllWithMember();
}
