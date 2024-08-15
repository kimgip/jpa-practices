package ex01.domain;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.persistence.*;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

@Slf4j
@SpringBootTest
public class TestTransactionBoundary01 {

    @PersistenceUnit
    private EntityManagerFactory emf;

    @Test
    @Order(0)
    void beforeAllTest() {
        // 1. in EntityTransaction Boundary
        Book book01 = new Book("book01", "Mastering JPA");
        EntityManager em01 = emf.createEntityManager();
        EntityTransaction tx = em01.getTransaction();

        tx.begin();
        /* [      Transaction Begin      ] */

        em01.persist(book01);

        /* [  Transaction End(Success)  ] */
        tx.commit();


        // 2. Not in EntityTransaction Boundary
        Book book02 = new Book("book02", "Mastering JPA");
        EntityManager em02 = emf.createEntityManager();

        em02.persist(book02);
    }

    @Test
    @Order(1)
    void testInEntityTransactionBoundary() {
        EntityManager em = emf.createEntityManager();
        assertNotNull(em.find(Book.class, "book01"));
    }

    @Test
    @Order(2)
    void testNotInEntityTransactionBoundary() {
        EntityManager em = emf.createEntityManager();
        assertNull(em.find(Book.class, "book02"));
    }
}