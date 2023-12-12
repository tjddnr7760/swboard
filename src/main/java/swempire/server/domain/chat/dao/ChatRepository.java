package swempire.server.domain.chat.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import swempire.server.domain.chat.domain.Chat;

public interface ChatRepository extends JpaRepository<Chat, Long> {
}
