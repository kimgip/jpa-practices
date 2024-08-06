package ex01.repository;

import ex01.domain01_05.Pet;
import ex01.domain06.Emaillist;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Slf4j
@Getter
@Repository
public class EmaillistRepository {

    @PersistenceContext
    public EntityManager em;

    public void save(Emaillist email) {
        em.persist(email);
    }
}
