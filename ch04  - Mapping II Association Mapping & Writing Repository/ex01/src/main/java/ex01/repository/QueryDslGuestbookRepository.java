package ex01.repository;

import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import ex01.domain.Guestbook;
import ex01.dto.GuestbookDto;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import org.springframework.stereotype.Repository;
import java.util.List;

import static ex01.domain.QGuestbook.guestbook;

@Repository
public class QueryDslGuestbookRepository extends QuerydslRepositorySupport {

    private JPAQueryFactory queryFactory;

    public QueryDslGuestbookRepository(JPAQueryFactory queryFactory) {
        super(Guestbook.class);
        this.queryFactory = queryFactory;
    }

    // 저장
    public void save(Guestbook guestbook){
        getEntityManager().persist(guestbook);
    }

    // 조회01
    public List<Guestbook> findAll01(){
        return (List<Guestbook>) queryFactory
                .select(guestbook)
                .from(guestbook)
                .orderBy(guestbook.regDate.desc())
                .fetch();
    }

    // 조회02: projection
    public List<GuestbookDto> findAll02(){
        return queryFactory
                .select(Projections.constructor(GuestbookDto.class, guestbook.id, guestbook.name, guestbook.contents, guestbook.regDate))
                .from(guestbook)
                .orderBy(guestbook.regDate.desc())
                .fetch();
    }

    // 삭제
    public Long delete(Integer id, String password) {
        return queryFactory
                .delete(guestbook)
                .where(guestbook.id.eq(id).and(guestbook.password.eq(password)))
                .execute();
    }

    // count
    public Long count() {
        return queryFactory
                .from(guestbook)
                .fetchCount();
    }
}
