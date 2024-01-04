package swempire.server.domain.chat.application;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import swempire.server.domain.board.domain.Board;
import swempire.server.domain.chat.dao.ChatRepository;
import swempire.server.domain.chat.domain.Chat;
import swempire.server.domain.chat.dto.ChatResponse;
import swempire.server.domain.member.domain.Member;

import java.util.List;
import java.util.Optional;

@Slf4j
@RequiredArgsConstructor
@Service
public class ChatService {

    private final ChatRepository chatRepository;

    public List<ChatResponse> getAllChatsInOneBoard(Long boardIndex) {
        return chatRepository.getAllChatsByBoardIndex(boardIndex);
    }

    public void saveChat(Long boardIndex, String body, String email, Long parentChatId) {
        Optional<Board> optionalBoard = chatRepository.findByBoardId(boardIndex);
        Optional<Member> optionalMember = chatRepository.findMemberByEmail(email);
        Optional<Chat> optionalChat = chatRepository.getLastChatById(parentChatId);
        Long chatNumber = chatRepository.findChatCountByBoardId(boardIndex);

        if (parentChatId == 0L && (optionalBoard.isPresent() && optionalMember.isPresent())) {
            log.info("ParentChat Id is zero, default chat");
            if (chatNumber == 0L) {
                log.info("First chat in BoardId {}", boardIndex);
                chatRepository.save(
                        Chat.initOf(
                                body,
                                optionalBoard.get(),
                                optionalMember.get())
                );
            } else if (optionalChat.isPresent()) {
                log.info("Default Depth Chat in BoardId {}", boardIndex);
                Chat chat = optionalChat.get();
                log.info("search chat parentChatId : {}, sequence: {}", chat.getId(), chat.getSequence());
                chatRepository.save(
                        Chat.parentChatIdZeroOf(
                                chat,
                                body,
                                optionalBoard.get(),
                                optionalMember.get())
                );
            }
        } else if (parentChatId >= 1L && (optionalBoard.isPresent() && optionalMember.isPresent())) {
            if (optionalChat.isPresent()) {
                log.info("Reply chat, parentChatId {}", parentChatId);
                Chat chat = optionalChat.get();
                log.info("search chat parentChatId : {}, sequence: {}", chat.getId(), chat.getSequence());
                chatRepository.save(
                        Chat.defaultOf(
                                chat,
                                body,
                                parentChatId,
                                optionalBoard.get(),
                                optionalMember.get())
                );
            } else {
                log.info("First reply chat, parentChatId : {}", parentChatId);
                chatRepository.save(
                        Chat.initReplyOf(
                                parentChatId,
                                body,
                                optionalBoard.get(),
                                optionalMember.get()
                        )
                );
            }
        } else {
            throw new IllegalStateException("Invalid Chat Situation");
        }
    }

    public void updateChat(Long id, String body, String email) {
        Chat chat = chatRepository.findById(id)
                .orElseThrow(() -> new IllegalStateException("Chat not found with id: " + id));

        if (chat.getMember().getEmail().equals(email)) {
            chat.updateBody(body);
            chatRepository.save(chat);
            log.info("chat update done, chat id : {}", id);
        } else {
            throw new IllegalStateException("This chat is not users");
        }
    }

    public void deleteChat(Long id, String email) {
        Chat chat = chatRepository.findById(id)
                .orElseThrow(() -> new IllegalStateException("Chat not found with id: " + id));

        if (chat.getMember().getEmail().equals(email)) {
            chatRepository.delete(chat);
            log.info("chat delete done");
        } else {
            throw new IllegalStateException("This chat is not users");
        }
    }
}
