package swempire.server.domain.board.application;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import swempire.server.domain.board.dao.BoardRepository;
import swempire.server.domain.board.domain.Board;
import swempire.server.domain.board.dto.BoardBodyResponse;
import swempire.server.domain.board.dto.BoardResponse;
import swempire.server.domain.member.domain.Member;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@RequiredArgsConstructor
@Service
public class BoardService {

    private final BoardRepository boardRepository;

    public void postSave(String email, String title, String body) {
        Member memberByEmail = boardRepository.findMemberByEmail(email);
        log.info("find member is : {}", memberByEmail.getEmail());

        boardRepository.save(Board.of(title, body, memberByEmail));
    }

    public List<BoardResponse> getAllBoards() {
        List<Board> boards = boardRepository.findAllWithMember();

        return boards.stream()
                .map(board -> new BoardResponse(board.getId(), board.getTitle(), board.getCreatedAt(), board.getMember().getName()))
                .collect(Collectors.toList());
    }

    public BoardBodyResponse getSpecificBoard(Long boardId) {
        Optional<Board> optionalBoard = boardRepository.findById(boardId);
        BoardBodyResponse boardBodyResponse;
        if (optionalBoard.isPresent()) {
            Board board = optionalBoard.get();
            boardBodyResponse = BoardBodyResponse.builder()
                    .id(board.getId())
                    .title(board.getTitle())
                    .name(board.getMember().getName())
                    .body(board.getBody())
                    .createdAt(board.getCreatedAt())
                    .build();
        } else {
            throw new RuntimeException();
        }
        return boardBodyResponse;
    }

    public void postEditSave(Long boardIndex, String email, String title, String body) {
        Board existingBoard = boardRepository.findById(boardIndex)
                .orElseThrow(() -> new IllegalStateException("Board not found with id: " + boardIndex));

        Member memberByEmail = boardRepository.findMemberByEmail(email);
        log.info("token id = {}, path-id = {}", memberByEmail.getId(), existingBoard.getMember().getId());
        if (Objects.equals(existingBoard.getMember().getId(), memberByEmail.getId())) {
            existingBoard.updateBoard(title, body);
            boardRepository.save(existingBoard);
        } else {
            throw new IllegalStateException("this user is not owner");
        }
    }

    public void boardDelete(Long boardIndex, String email) {
        Board existingBoard = boardRepository.findById(boardIndex)
                .orElseThrow(() -> new IllegalStateException("Board not found with id: " + boardIndex));

        Member memberByEmail = boardRepository.findMemberByEmail(email);
        log.info("token id = {}, path-id = {}", memberByEmail.getId(), existingBoard.getMember().getId());
        if (Objects.equals(existingBoard.getMember().getId(), memberByEmail.getId())) {
            boardRepository.delete(existingBoard);
        } else {
            throw new IllegalStateException("this user is not owner");
        }
    }
}
