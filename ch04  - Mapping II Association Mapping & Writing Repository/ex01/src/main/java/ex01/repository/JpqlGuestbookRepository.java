package ex01.repository;

import ex01.domain.Guestbook;
import ex01.dto.GuestbookDto;
import org.springframework.stereotype.Repository;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import java.util.List;

@Repository
public class JpqlGuestbookRepository {
    @PersistenceContext
    private EntityManager em;

    // 저장
    public void save(Guestbook guestbook) {
        em.persist(guestbook);
    }

    // 삭제
    public Integer delete(Integer id, String password) {
        String qlString = "delete from Guestbook gb where gb.id=:id and gb.password=:password";
        Query query = em.createQuery(qlString);
        query.setParameter("id", id);
        query.setParameter("password", password);
        return query.executeUpdate();
    }

    // 조회01
    public List<Guestbook> findAll01() {
        String qlString = "select gb from Guestbook gb order by gb.regDate desc";
        TypedQuery<Guestbook> query = em.createQuery(qlString, Guestbook.class);
        return query.getResultList();
    }

    // 조회02: projection
    public List<GuestbookDto> findAll02() {
        String qlString = "select new ex01.dto.GuestbookDto(gb.id, gb.name, gb.contents, gb.regDate) from Guestbook gb order by gb.regDate desc";
        TypedQuery<GuestbookDto> query = em.createQuery(qlString, GuestbookDto.class);
        return query.getResultList();
    }

    // count
    public Long count() {
        TypedQuery<Long> query = em.createQuery("select count(gb) from Guestbook gb", Long.class);
        return query.getSingleResult();
    }
}