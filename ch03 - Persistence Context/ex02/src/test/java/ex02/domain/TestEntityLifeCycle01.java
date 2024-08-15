package ex02.domain;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.boot.test.context.SpringBootTest;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.PersistenceUnit;

import static org.junit.jupiter.api.Assertions.*;

@Slf4j
@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class TestEntityLifeCycle01 {

    @PersistenceUnit
    private EntityManagerFactory emf;

    @Test
    @Order(0)
    void testPersist() {
        // 엔티티매니저 생성
        EntityManager em = emf.createEntityManager();
        
        // 트랜잭션 객체 얻어오기
        EntityTransaction tx = em.getTransaction();

        // [트랜잭션 시작]
        tx.begin();

        Book book = new Book("book", "Mastering JPA");

        // 영속화
        em.persist(book);

        tx.commit();
        // [트랜잭션 종료]
        
        em.close(); // flush가 이때 됨
    }

    @Test
    @Order(1)
    void testFind() {
        EntityManager em = emf.createEntityManager();

        // JPQ O: 그리고 1차 캐시에 저장됨
        Book book01 = em.find(Book.class, "book");
        assertNotNull(book01);

        // JPQ X: 1차 캐시에서 가져옴
        Book book02 = em.find(Book.class, "book");
        assertTrue(book01 == book02);

        em.close();
    }

    @Test
    @Order(2)
    void testUpdate() {
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();

        tx.begin();

        Book book = em.find(Book.class, "book");
        book.setTitle("Mastering JPA ed.2");

        tx.commit();

        em.close();
    }

    @Test
    @Order(3)
    void testRemove() {
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();

        tx.begin();

        Book book = em.find(Book.class, "book");

        // 비영속화/db에서도 삭제
        em.remove(book);

        tx.commit();

        em.close();
    }

}