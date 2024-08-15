package ex01.domain;

import ex01.repository.BookRepository03;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceUnit;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@Slf4j
@SpringBootTest
public class TestTransactionBoundary04 {
    @PersistenceUnit
    private EntityManagerFactory emf;

    @Autowired
    BookRepository03 bookRepository;

    @Test
    @Order(0)
    @Transactional
    @Rollback(false)
    void beforeAllTest() {
        Book book01 = new Book("book01", "Mastering JPA");
        bookRepository.save(book01);
    }

    @Test
    @Order(1)
    void testInEntityTransactionBoundary() {
        EntityManager em = emf.createEntityManager();
        assertNotNull(em.find(Book.class, "book01"));
    }
}