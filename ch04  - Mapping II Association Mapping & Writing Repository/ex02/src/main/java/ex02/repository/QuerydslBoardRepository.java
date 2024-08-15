package ex02.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import ex02.domain.Board;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import org.springframework.stereotype.Repository;

import java.util.List;

import static ex02.domain.QBoard.board;

@Repository
public class QuerydslBoardRepository extends QuerydslRepositorySupport {

    private JPAQueryFactory queryFactory;

    public QuerydslBoardRepository(JPAQueryFactory queryFactory) {
        super(Board.class);
        this.queryFactory = queryFactory;
    }

    // 저장: 영속화
    public void save(Board board) {
        getEntityManager().persist(board);
    }

    // 조회01: Fetch One
    public Board findById01(Integer id) {
        return getEntityManager().find(Board.class, id);
    }

    // 조회02: Fetch One: JPQL
    public Board findById02(Integer id) {
        return (Board) queryFactory
                .from(board)
                .where(board.id.eq(id))
                .fetchOne();
    }

    // 조회03: Fetch List
    public List<Board> findAll01() {
        return queryFactory
                .select(board)
                .from(board)
                .orderBy(board.regDate.desc())
                .fetch();
    }

    // 조회04: Fetch List: Inner Join 사용
    public List<Board> findAll02() {
        return queryFactory
                .select(board)
                .from(board)
                .innerJoin(board.user())
                .orderBy(board.regDate.desc())
                .fetch();
    }

    // 조회05: Fetch List: Fetch Join 사용
    public List<Board> findAll03() {
        return queryFactory
                .select(board)
                .from(board)
                .innerJoin(board.user()).fetchJoin()
                .orderBy(board.regDate.desc())
                .fetch();
    }

    // 조회06: Fetch List: Fetch Join 사용: Paging(size of each)
    public List<Board> findAll03(Integer page, Integer size) {
        return queryFactory
                .select(board)
                .from(board)
                .innerJoin(board.user()).fetchJoin()
                .offset(page * size)
                .limit(size)
                .orderBy(board.regDate.desc())

                .fetch();
    }

    // 조회07: Fetch List: Fetch Join 사용: Paging(size of each): Like Searching
    public List<Board> findAll03(String keyword, Integer page, Integer size) {
        return queryFactory
                .select(board)
                .from(board)
                .innerJoin(board.user()).fetchJoin()
                .where(board.title.contains(keyword).or(board.contents.contains(keyword)))
                .orderBy(board.regDate.desc())
                .offset(page * size)
                .limit(size)
                .fetch();
    }

    // 수정01: 영속객체 사용
    public Board update01(Board board) {
        Board boardPersisted = getEntityManager().find(Board.class, board.getId());

        if (boardPersisted != null) {
            boardPersisted.setTitle(board.getTitle());
            boardPersisted.setContents(board.getContents());
        }

        return boardPersisted;
    }

    // 수정02: JPQL 사용
    public Long update02(Board argBoard) {
        return queryFactory
                .update(board)
                .set(board.title, argBoard.getTitle())
                .set(board.contents, argBoard.getContents())
                .where(board.id.eq(argBoard.getId()))
                .execute();
    }

    // 삭제01: 영속객체 사용
    public void delete01(Integer id) {
        Board board = getEntityManager().find(Board.class, id);
        getEntityManager().remove(board);
    }

    // 삭제02: JPQL 사용
    public Long delete02(Integer id) {
        return queryFactory
                .delete(board)
                .where(board.id.eq(id))
                .execute();
    }

    // 삭제03: JPQL 사용: 비즈니스 로직(게시물 번호와 사용자 번호로 삭제)
    public Long delete02(Integer id, Integer userId) {
        return queryFactory
                .delete(board)
                // 다음 2개의 where 메소드는 완전 동일
                // .where(board.id.eq(id).and(board.user().id.eq(userId)))
                .where(board.id.eq(id), board.user().id.eq(userId))
                .execute();
    }

    // count
    public Long count() {
        return queryFactory
                .from(board)
                .fetchCount();
    }
}