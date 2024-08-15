package ex02.repository;

import com.sun.istack.NotNull;
import ex02.domain.Board;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import java.util.List;

@RequiredArgsConstructor
@Repository
public class JpqlBoardRepository {

    @NotNull
    private EntityManager em;

    // 저장: 영속화
    public void save(Board board) {
        em.persist(board);
    }

    // 조회01: Fetch One
    public Board findById01(Integer id) {
        return em.find(Board.class, id);
    }

    // 조회02: Fetch One: JPQL
    public Board findById02(Integer id) {
        String qlString = "select b from Board b where b.id = :id";
        TypedQuery<Board> query = em.createQuery(qlString, Board.class);

        query.setParameter("id", id);
        return query.getSingleResult();
    }

    // 조회03: Fetch List:
    public List<Board> findAll01() {
        String qlString = "select b from Board b order by b.regDate desc";
        TypedQuery<Board> query = em.createQuery(qlString, Board.class);

        return query.getResultList();
    }

    // 조회04: Fetch List: Inner Join 사용
    public List<Board> findAll02() {
        String qlString = "select b from Board b inner join b.user u order by b.regDate desc";
        TypedQuery<Board> query = em.createQuery(qlString, Board.class);

        return query.getResultList();
    }

    // 조회05: Fetch List: Fetch Join 사용
    public List<Board> findAll03() {
        String qlString = "select b from Board b join fetch b.user order by b.regDate desc";
        TypedQuery<Board> query = em.createQuery(qlString, Board.class);

        return query.getResultList();
    }

    // 조회06: Fetch List: Fetch Join 사용: Paging(size of each)
    public List<Board> findAll03(Integer page, Integer size) {
        String qlString = "select b from Board b join fetch b.user order by b.regDate desc";
        TypedQuery<Board> query = em.createQuery(qlString, Board.class);

        query.setFirstResult((page - 1) * size);
        query.setMaxResults(size);

        return query.getResultList();
    }

    // 조회07: Fetch List: Fetch Join 사용: Paging(size of each): Like Searching
    public List<Board> findAll03(String keyword, Integer page, Integer size) {
        String qlString = "select b from Board b join fetch b.user where b.title like :keywordContains or b.contents like :keywordContains order by b.regDate desc";
        TypedQuery<Board> query = em.createQuery(qlString, Board.class);

        query.setParameter("keywordContains", "%" + keyword + "%");
        query.setFirstResult((page - 1) * size);
        query.setMaxResults(size);

        return query.getResultList();
    }

    // 수정01: 영속객체 사용
    public Board update01(Board board) {
        Board boardPersisted = em.find(Board.class, board.getId());

        if (boardPersisted != null) {
            boardPersisted.setTitle(board.getTitle());
            boardPersisted.setContents(board.getContents());
        }

        return boardPersisted;
    }

    // 수정02: JPQL 사용
    public Integer update02(Board board) {
        Query query = em.createQuery("update Board b set b.title=:title, b.contents=:contents where b.id=:id");

        query.setParameter("id", board.getId());
        query.setParameter("title", board.getTitle());
        query.setParameter("contents", board.getContents());

        return query.executeUpdate();
    }

    // 삭제01: 영속객체 사용
    public void delete01(Integer id) {
        Board board = em.find(Board.class, id);
        em.remove(board);
    }

    // 삭제02: JPQL 사용
    public Integer delete02(Integer id) {
        String qlString = "delete from Board b where b.id=:id";
        Query query = em.createQuery(qlString);
        query.setParameter("id", id);

        return query.executeUpdate();
    }

    // 삭제03: JPQL 사용: 비즈니스 로직(게시물 번호와 사용자 번호로 삭제)
    public Integer delete02(Integer id, Integer userId) {
        String qlString = "delete from Board b where b.id = ?1 and b.user.id = ?2";
        Query query = em.createQuery(qlString);

        query.setParameter(1, id);
        query.setParameter(2, userId);

        return query.executeUpdate();
    }

    // count
    public Long count() {
        String qlString = "select count(b) from Board b";
        TypedQuery<Long> query = em.createQuery(qlString, Long.class);
        return query.getSingleResult();
    }
}
