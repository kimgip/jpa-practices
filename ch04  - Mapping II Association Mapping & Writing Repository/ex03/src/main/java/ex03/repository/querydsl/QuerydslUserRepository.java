package ex03.repository.querydsl;

import ex03.domain.Order;
import ex03.domain.User;
import ex03.domain.dto.UserDto;

import java.util.List;

public interface QuerydslUserRepository {
    public UserDto findById02(Integer id);
    public Long update(User user);

    public List<Order> findOrdersById(Integer id);

    public List<User> findAllCollectionJoinProblem();
    public List<User> findAllCollectionJoinProblemSolved();
    public List<User> findAllCollectionJoinAndNplusOneProblemSolved();
}
