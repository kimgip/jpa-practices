package ex01.domain;

import ex01.repository.BookRepository03;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceUnit;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

@Slf4j
@SpringBootTest
public class TestTransactionBoundary03 {
    @PersistenceUnit
    private EntityManagerFactory emf;

    @Autowired
    private PlatformTransactionManager transactionManager;

    @Autowired
    BookRepository03 bookRepository;

    @Test
    @Order(0)
    void beforeAllTest() {
        Book book01 = new Book("book01", "Mastering JPA");

        TransactionStatus status = transactionManager.getTransaction(new DefaultTransactionDefinition());
        /* [      Transaction Begin      ] */

        bookRepository.save(book01);

        /* [  Transaction End(Success)  ] */
        transactionManager.commit(status);
    }

    @Test
    @Order(1)
    void testInEntityTransactionBoundary() {
        EntityManager em = emf.createEntityManager();
        assertNotNull(em.find(Book.class, "book01"));
    }
}