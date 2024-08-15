package ex01.repository;

import ex01.domain.Guestbook;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@Slf4j
@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class TestJpaGuestbookRepository {
    private static Guestbook guesbookMock01 = new Guestbook("고길동", "1234", "안녕!", new Date());
    private static Guestbook guesbookMock02 = new Guestbook("고철수", "1234", "안녕하세요!", new Date());
    private static Guestbook guesbookMock03 = new Guestbook("고영희", "1234", "안녕하세요!", new Date());
    private static Long countGuestbook;

    @Autowired
    private JpaGuestbookRepository guestbookRepository;

    @Test
    @Order(0)
    @Transactional
    @Rollback(false)
    public void testSave() {
        guestbookRepository.save(guesbookMock01);
        guestbookRepository.save(guesbookMock02);
        guestbookRepository.save(guesbookMock03);

        assertNotNull(guesbookMock01.getId());
        assertNotNull(guesbookMock02.getId());
        assertNotNull(guesbookMock03.getId());

        countGuestbook = guestbookRepository.count();
    }

    @Test
    @Order(1)
    public void testFindAll() {
        List<Guestbook> list = guestbookRepository.findAll();
        assertEquals(countGuestbook, list.size());
    }

    @Test
    @Order(2)
    public void testFindAllSortByRegDateAsc() {
        List<Guestbook> list = guestbookRepository.findAll(Sort.by(Sort.Direction.ASC, "regDate"));
        assertEquals(countGuestbook, list.size());
    }

    @Test
    @Order(3)
    public void testFindAllPagination() {
        Page<Guestbook> page = guestbookRepository.findAll(PageRequest.of(0, 2, Sort.Direction.DESC, "regDate"));
        List<Guestbook> list = page.getContent();
        assertEquals(2, list.size());
    }

    @Test
    @Order(4)
    public void testFindAllByOrderByRegDateDesc() {
        List<Guestbook> list = guestbookRepository.findAllByOrderByRegDateDesc();
        assertEquals(countGuestbook, list.size());
    }

    @Test
    @Order(5)
    @Transactional
    @Rollback(false)
    public void testFindByIdAndDelete(){
        Guestbook guestbook = guestbookRepository.findById(guesbookMock01.getId()).orElse(null);
        assertNotNull(guestbook);

        guestbookRepository.delete(guestbook);
        assertEquals(--countGuestbook, guestbookRepository.count());
    }

    @Test
    @Order(6)
    @Transactional
    @Rollback(false)
    public void testDeleteById(){
        guestbookRepository.deleteById(guesbookMock02.getId());
        assertEquals(--countGuestbook, guestbookRepository.count());
    }

    @Test
    @Order(7)
    @Transactional
    @Rollback(false)
    public void testDeleteByIdAndPassword() {
        assertTrue(guestbookRepository.deleteByIdAndPassword(guesbookMock03.getId(), "1234") == 1);
        assertEquals(--countGuestbook, guestbookRepository.count());
    }
}
