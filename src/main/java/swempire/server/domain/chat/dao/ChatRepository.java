package swempire.server.domain.chat.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import swempire.server.domain.chat.domain.Chat;

@Repository
public interface ChatRepository extends JpaRepository<Chat, Long> {
}
