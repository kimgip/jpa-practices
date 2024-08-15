package ex01.repository;

import ex01.domain.Book;
import lombok.extern.slf4j.Slf4j;
import org.springframework.orm.jpa.EntityManagerHolder;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.support.TransactionSynchronizationManager;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceUnit;

@Slf4j
@Repository
public class BookRepository02 {

    @PersistenceUnit
    private EntityManagerFactory emf;

    public void save(Book book) {
        EntityManagerHolder emHolder = (EntityManagerHolder) TransactionSynchronizationManager.getResource(emf);
        EntityManager em = emHolder.getEntityManager();

        em.persist(book);
    }
}