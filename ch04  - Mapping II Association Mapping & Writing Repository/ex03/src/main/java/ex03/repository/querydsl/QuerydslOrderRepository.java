package ex03.repository.querydsl;

import ex03.domain.Order;
import ex03.domain.dto.OrderCountOfUserDto;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.List;

public interface QuerydslOrderRepository {
    List<Order> findAllByUserNo02(Integer userId);
    List<Order> findAllByUserNo02(Integer userId, Sort sort);
    List<Order> findAllByUserNo02(Integer userId, Pageable pageable);

    List<OrderCountOfUserDto> countAllGroupByUser();
}
