package ex02.repository;

import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import ex02.domain.User;
import ex02.domain.dto.UserDto;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import org.springframework.stereotype.Repository;

import static ex02.domain.QUser.user;

@Repository
public class QuerydslUserRepository extends QuerydslRepositorySupport {

    private JPAQueryFactory queryFactory;

    public QuerydslUserRepository(JPAQueryFactory queryFactory) {
        super(User.class);
        this.queryFactory = queryFactory;
    }

    // 저장: 영속화
    public void save(User user) {
        getEntityManager().persist(user);
    }

    // 조회01: Fetch One
    public User findById01(Long no) {
        return getEntityManager().find(User.class, no);
    }

    // 조회02: Fetch One: JPQL 사용
    public User findById02(Integer id) {
        return (User) queryFactory
                .from(user)
                .where(user.id.eq(id))
                .fetchOne();
    }

    // 조회03: Fetch One: JPQL 사용: Projection
    public UserDto findByEmailAndPassword(String email, String password) {
        return queryFactory
                .select(Projections.constructor(UserDto.class, user.id, user.name))
                .from(user)
                // 다음 2개의 where 메소드는 완전 동일
                // .where(user.email.eq(email).and(user.password.eq(password)))
                .where(user.email.eq(email), user.password.eq(password))
                .fetchOne();
    }

    // 수정01: 영속객체 사용
    public User update01(User user) {
        User userPersisted = getEntityManager().find(User.class, user.getId());

        if (userPersisted != null) {
            userPersisted.setRole(user.getRole());
            userPersisted.setGender(user.getGender());
            userPersisted.setEmail(user.getEmail());
            userPersisted.setName(user.getName());
            userPersisted.setPassword(user.getPassword());
        }

        return userPersisted;
    }

    // 수정02: JPQL 사용
    public Long update02(User argUser) {
        return queryFactory.update(user)
                .where(user.id.eq(argUser.getId()))
                .set(user.name, argUser.getName())
                .set(user.email, argUser.getEmail())
                .set(user.password, argUser.getPassword())
                .set(user.gender, argUser.getGender())
                .set(user.role, argUser.getRole())
                .execute();
    }


    // 삭제01: 영속객체 사용
    public void delete01(Integer id) {
        User user = getEntityManager().find(User.class, id);
        getEntityManager().remove(user);
    }

    // 삭제02: JPQL 사용
    public Long delete02(Integer id) {
        return queryFactory
                .delete(user)
                .where(user.id.eq(id))
                .execute();
    }

    // count
    public Long count() {
        return queryFactory
                .from(user)
                .fetchCount();
    }
}