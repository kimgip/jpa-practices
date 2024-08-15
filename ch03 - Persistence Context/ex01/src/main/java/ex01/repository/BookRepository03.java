package ex01.repository;

import ex01.domain.Book;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Slf4j
@Repository
public class BookRepository03 {

    @PersistenceContext
    private EntityManager em;

    public void save(Book book) {
        em.persist(book);
    }
}