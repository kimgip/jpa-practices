package ex03.repository.querydsl;

import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.PathBuilder;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import ex03.domain.Order;
import ex03.domain.dto.OrderCountOfUserDto;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;

import java.util.List;

import static ex03.domain.QOrder.order;

public class QuerydslOrderRepositoryImpl extends QuerydslRepositorySupport implements QuerydslOrderRepository {
    private JPAQueryFactory queryFactory;

    public QuerydslOrderRepositoryImpl(JPAQueryFactory queryFactory) {
        super(Order.class);
        this.queryFactory = queryFactory;
    }

    @Override
    public List<Order> findAllByUserNo02(Integer userId) {
        return (List<Order>) queryFactory
                .select(order)
                .from(order)
                .innerJoin(order.user())
                .fetchJoin()
                .where(order.user().id.eq(userId))
                .fetch();
    }

    @Override
    public List<Order> findAllByUserNo02(Integer userId, Sort sort) {
        JPAQuery<Order> query = queryFactory
                .select(order)
                .from(order)
                .innerJoin(order.user())
                .fetchJoin()
                .where(order.user().id.eq(userId));

        for (Sort.Order o : sort) {
            PathBuilder orderByExpression = new PathBuilder(Order.class, "order");
            query.orderBy(new OrderSpecifier(o.isAscending() ? com.querydsl.core.types.Order.ASC : com.querydsl.core.types.Order.DESC, orderByExpression.get(o.getProperty())));
        }

        return query.fetch();
    }

    @Override
    public List<Order> findAllByUserNo02(Integer userId, Pageable pageable) {
        JPAQuery<Order> query = queryFactory
                .select(order)
                .from(order)
                .innerJoin(order.user())
                .fetchJoin()
                .where(order.user().id.eq(userId));

        if (pageable != null) {
            query.offset(pageable.getOffset());
            query.limit(pageable.getPageSize());

            for (Sort.Order o : pageable.getSort()) {
                PathBuilder orderByExpression = new PathBuilder(Order.class, "order");
                query.orderBy(new OrderSpecifier(o.isAscending() ? com.querydsl.core.types.Order.ASC : com.querydsl.core.types.Order.DESC, orderByExpression.get(o.getProperty())));
            }
        }

        return query.fetch();
    }

    @Override
    public List<OrderCountOfUserDto> countAllGroupByUser(){
        return queryFactory
                .select(Projections.constructor(OrderCountOfUserDto.class, order.user().id, order.user().id.count()))
                .from(order)
                .innerJoin(order.user())
                .groupBy(order.user())
                .fetch();
    }
}