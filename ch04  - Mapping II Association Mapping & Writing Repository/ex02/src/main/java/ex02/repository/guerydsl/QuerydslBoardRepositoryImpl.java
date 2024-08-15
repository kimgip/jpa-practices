package ex02.repository.guerydsl;

import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.PathBuilder;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import ex02.domain.Board;
import ex02.domain.dto.BoardDto;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;

import java.util.List;

import static ex02.domain.QBoard.board;

public class QuerydslBoardRepositoryImpl extends QuerydslRepositorySupport implements QuerydslBoardRepository {

    private JPAQueryFactory queryFactory;

    public QuerydslBoardRepositoryImpl(JPAQueryFactory queryFactory) {
        super(Board.class);
        this.queryFactory = queryFactory;
    }

    @Override
    public BoardDto findById02(Integer id) {
        return queryFactory
                .select(Projections.fields(BoardDto.class, board.id, board.hit, board.title, board.contents, board.regDate, board.user().name.as("userName")))
                .from(board)
                .innerJoin(board.user())
                .where(board.id.eq(id))
                .fetchOne();
    }

    @Override
    public List<Board> findAllByOrderByRegDateDesc02() {
        return queryFactory
                .select(board)
                .from(board)
                .innerJoin(board.user()).fetchJoin() // 칼럼 전부 다 가져옴, fetchJoin: projection 불가
                .orderBy(board.regDate.desc())
                .fetch();
    }

    @Override
    public List<BoardDto> findAllByOrderByRegDateDesc03() { // queryDSL best version
        return queryFactory
                .select(Projections.fields(BoardDto.class, board.id, board.hit, board.title, board.contents, board.regDate, board.user().name.as("userName")))
                .from(board)
                .innerJoin(board.user())
                .orderBy(board.regDate.desc())
                .fetch();
    }

    @Override
    public List<BoardDto> findAllByOrderByRegDateDesc03(Integer page, Integer size) {
        return queryFactory
                .select(Projections.fields(BoardDto.class, board.id, board.hit, board.title, board.contents, board.regDate, board.user().name.as("userName")))
                .from(board)
                .innerJoin(board.user())
                .orderBy(board.regDate.desc()) // sorting field 내부에 있음
                .offset(page * size)
                .limit(size)
                .fetch();
    }

    @Override
    public List<BoardDto> findAll02(Pageable pageable) { // sorting field 바깥에 있음, 유연함 => 추천
        JPAQuery<BoardDto> query = queryFactory
                .select(Projections.fields(BoardDto.class, board.id, board.hit, board.title, board.contents, board.regDate, board.user().name.as("userName")))
                .from(board)
                .innerJoin(board.user());

        if (pageable != null) {
            query.offset(pageable.getOffset());
            query.limit(pageable.getPageSize());

            for (Sort.Order o : pageable.getSort()) {
                PathBuilder orderByExpression = new PathBuilder(Board.class, "board");
                query.orderBy(new OrderSpecifier(o.isAscending() ? com.querydsl.core.types.Order.ASC : com.querydsl.core.types.Order.DESC, orderByExpression.get(o.getProperty())));
            }
        }

        return query.fetch();
    }

    @Override
    public List<BoardDto> findAll02(String keyword, Pageable pageable) { // 검색 기능 추가
        JPAQuery<BoardDto> query = queryFactory
                .select(Projections.fields(BoardDto.class, board.id, board.hit, board.title, board.contents, board.regDate, board.user().name.as("userName")))
                .from(board)
                .innerJoin(board.user())
                .where(board.title.contains(keyword).or(board.contents.contains(keyword)));

        if (pageable != null) {
            query.offset(pageable.getOffset());
            query.limit(pageable.getPageSize());
            for (Sort.Order o : pageable.getSort()) {
                PathBuilder orderByExpression = new PathBuilder(Board.class, "board");
                query.orderBy(new OrderSpecifier(o.isAscending() ? com.querydsl.core.types.Order.ASC : com.querydsl.core.types.Order.DESC, orderByExpression.get(o.getProperty())));
            }
        }

        return query.fetch();
    }

    @Override
    public Long update(Board argBoard) {
        return queryFactory
                .update(board)
                .set(board.title, argBoard.getTitle())
                .set(board.contents, argBoard.getContents())
                .where(board.id.eq(argBoard.getId()))
                .execute();
    }

    @Override
    public Long delete(Integer id) {
        return queryFactory
                .delete(board)
                .where(board.id.eq(id))
                .execute();
    }

    @Override
    public Long delete(Integer id, Integer userId) {
        return queryFactory
                .delete(board)
                .where(board.id.eq(id).and(board.user().id.eq(userId)))
                .execute();
    }
}
