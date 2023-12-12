package swempire.server.domain.board.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import swempire.server.domain.board.domain.Board;

public interface BoardRepository extends JpaRepository<Board, Long> {
}
