package ex02.repository;

import ex02.domain.Board;
import ex02.domain.User;
import ex02.domain.type.GenderType;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@Slf4j
@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class TestJpqlBoardRepository {
    private static final User user01 = new User("고길동", "gildong@gmail.com", "1234", GenderType.male);
    private static final User user02 = new User("김정자", "joengja@gmail.com", "1234", GenderType.female);
    private static final Board board01 = new Board("제목1", "내용1", user01);
    private static final Board board02 = new Board("제목2", "내용2", user02);
    private static final Board board03 = new Board("제목3", "내용3", user02);
    private static Long countBoard;

    @Autowired
    private JpqlUserRepository userRepository;

    @Autowired
    private JpqlBoardRepository boardRepository;

    @Test
    @Order(0)
    @Transactional
    @Rollback(false)
    public void testSave() {
        userRepository.save(user01);
        assertNotNull(user01.getId());

        userRepository.save(user02);
        assertNotNull(user02.getId());

        boardRepository.save(board01);
        assertNotNull(board01.getId());

        boardRepository.save(board02);
        assertNotNull(board02.getId());

        boardRepository.save(board03);
        assertNotNull(board03.getId());

        countBoard = boardRepository.count();
    }

    @Test
    @Order(1)
    @Transactional
    public void testFindById01() {
        Board board = boardRepository.findById01(board01.getId());

        assertNotNull(board);
        assertEquals("고길동", board.getUser().getName());
    }

    @Test
    @Order(2)
    @Transactional //  for Divisioning JPQL Logs
    public void testFindById02() {
        Board board = boardRepository.findById02(board02.getId());

        assertNotNull(board);
        assertEquals("김정자", board.getUser().getName());
    }

    @Test
    @Order(3)
    @Transactional //  for Divisioning JPQL Logs
    public void testFindAll01() {
        List<Board> list = boardRepository.findAll01();
        assertEquals(countBoard, list.size());
    }

    @Test
    @Order(4)
    @Transactional //  for Divisioning JPQL Logs
    public void testFindAll02() {
        List<Board> list = boardRepository.findAll02();
        assertEquals(countBoard, list.size());
    }

    @Test
    @Order(5)
    @Transactional //  for Divisioning JPQL Logs
    public void testFindAll03() {
        List<Board> list = boardRepository.findAll03();
        assertEquals(countBoard, list.size());
    }

    @Test
    @Order(6)
    @Transactional //  for Divisioning JPQL Logs
    public void testFindAll03Pagination() {
        List<Board> list = boardRepository.findAll03(1, 3);
        assertEquals(3, list.size());
    }

    @Test
    @Order(7)
    @Transactional //  for Divisioning JPQL Logs
    public void testFindAll03PaginationAndLikeSearch() {
        String keyword = "내용";

        List<Board> list = boardRepository.findAll03(keyword, 1, 3);
        assertEquals(3, list.size());
    }

    @Test
    @Order(8)
    @Transactional
    @Rollback(false)
    public void testUpdate01() {
        Board board = new Board();

        board.setId(board01.getId());
        board.setTitle("제목10");
        board.setContents("내용10");

        Board boardPersisted = boardRepository.update01(board);
        assertEquals("고길동", boardPersisted.getUser().getName());
    }

    @Test
    @Order(9)
    @Transactional
    @Rollback(false)
    public void testUpdate02() {
        Board board = new Board();

        board.setId(board02.getId());
        board.setTitle("제목20");
        board.setContents("내용20");

        assertEquals(1, boardRepository.update02(board));
    }

    @Test
    @Order(10)
    @Transactional
    @Rollback(false)
    public void testDelete01() {
        boardRepository.delete01(board01.getId());
        assertEquals(--countBoard, boardRepository.count());
    }

    @Test
    @Order(11)
    @Transactional
    @Rollback(false)
    public void testDelete02() {
        boardRepository.delete02(board02.getId());
        assertEquals(--countBoard, boardRepository.count());
    }

    @Test
    @Order(12)
    @Transactional
    @Rollback(false)
    public void testDelete02ByIdAndUserId() {
        boardRepository.delete02(board03.getId(), user02.getId());
        assertEquals(--countBoard, boardRepository.count());

        userRepository.delete01(user01.getId());
        userRepository.delete01(user02.getId());
    }
}