package ex01.domain;

import ex01.repository.BookRepository02;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.orm.jpa.EntityManagerHolder;
import org.springframework.transaction.support.TransactionSynchronizationManager;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.PersistenceUnit;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@Slf4j
@SpringBootTest
public class TestTransactionBoundary02 {
    @PersistenceUnit
    private EntityManagerFactory emf;

    @Autowired
    private BookRepository02 bookRepository;

    @Test
    @Order(0)
    void beforeAllTest() {
        Book book01 = new Book("book01", "Mastering JPA");

        EntityManager em = emf.createEntityManager();
        TransactionSynchronizationManager.bindResource(emf, new EntityManagerHolder(em));
        EntityTransaction tx = em.getTransaction();
        tx.begin();
        /* [      Transaction Begin      ] */

        bookRepository.save(book01);

        /* [  Transaction End(Success)  ] */
        tx.commit();
    }

    @Test
    @Order(1)
    void testInEntityTransactionBoundary() {
        EntityManager em = emf.createEntityManager();
        assertNotNull(em.find(Book.class, "book01"));
    }
}