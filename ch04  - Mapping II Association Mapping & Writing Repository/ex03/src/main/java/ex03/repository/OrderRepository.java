package ex03.repository;

import ex03.domain.Order;
import ex03.repository.querydsl.QuerydslOrderRepository;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Integer>, QuerydslOrderRepository {
	List<Order> findAllByUserId(Integer userId);
	List<Order> findAllByUserId(Integer userId, Sort sort);
	Long countAllByUserId(Integer userId);
}
