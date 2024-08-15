package ex03.repository;

import ex03.domain.Order;
import ex03.domain.User;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@Slf4j
@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class TestUserRepository {

    private static User[] users = new User[] {new User("둘리"), new User("마이콜"), new User("또치")};
    private static Order[] orders = new Order[] { new Order("order01"), new Order("order02"), new Order("order03"), new Order("order04"), new Order("order05"), new Order("order06")};

    private static Long countOrders;

    private static Long countUsers;

    @PersistenceContext
    private EntityManager em;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private OrderRepository orderRepository;

    @Test
    @org.junit.jupiter.api.Order(0)
    @Transactional
    @Rollback(false)
    public void testSave(){
        userRepository.save(users[0]);
        assertNotNull(users[0].getId());

        orders[0].setUser(users[0]);
        orderRepository.save(orders[0]);
        assertNotNull(orders[0].getId());

        orders[1].setUser(users[0]);
        orderRepository.save(orders[1]);
        assertNotNull(orders[1].getId());

        orders[2].setUser(users[0]);
        orderRepository.save(orders[2]);
        assertNotNull(orders[2].getId());

        //================================

        userRepository.save(users[1]);
        assertNotNull(users[1].getId());

        orders[3].setUser(users[1]);
        orderRepository.save(orders[3]);
        assertNotNull(orders[3].getId());

        orders[4].setUser(users[1]);
        orderRepository.save(orders[4]);
        assertNotNull(orders[4].getId());

        //================================

        userRepository.save(users[2]);
        assertNotNull(users[2].getId());

        orders[5].setUser(users[2]);
        orderRepository.save(orders[5]);
        assertNotNull(orders[5].getId());

        //================================

        countOrders = orderRepository.count();
        countUsers = userRepository.count();
    }

    @Test
    @org.junit.jupiter.api.Order(1)
    @Transactional
    @Rollback(false)
    public void testUpdate() {
        User user = new User();
        user.setId(users[0].getId());
        user.setEmail("dooly@kickscar.me");
        user.setName("둘리");
        user.setPassword("1234");
        user.setPhone("000-0000-0000");

        assertEquals(1L, userRepository.update(user));
    }

    @Test
    @org.junit.jupiter.api.Order(2)
    @Transactional // for Divisioning JPQL Logs
    public void testFindOrderById() {
        User user = userRepository.findOrderById(users[0].getId());
        List<Order> orders = user.getOrders();

        assertEquals(3, orders.size());
    }

    @Test
    @org.junit.jupiter.api.Order(3)
    @Transactional // for Divisioning JPQL Logs
    public void testFindOrdersById() {
        User user = userRepository.findById(users[0].getId()).get(); // 위험한 코드: optional 처리 후 get할 것!
        List<Order> orders = user.getOrders();

        assertEquals(3, orders.size());
    }

    @Test
    @org.junit.jupiter.api.Order(4)
    @Transactional // for Divisioning JPQL Logs
    public void testFindAllCollectionJoinProblem() {
        List<User> users = userRepository.findAllCollectionJoinProblem();

        for(User user : users) {
            System.out.println(user); // 다중 로그 발생
        }

        assertEquals(countOrders, users.size());
    }


    @Test
    @org.junit.jupiter.api.Order(5)
    @Transactional // for Divisioning JPQL Logs
    public void testCollectionJoinProblemSolved() {
        List<User> users = userRepository.findAllCollectionJoinProblemSolved();

        for(User user : users) {
            System.out.println(user);
        }

        assertEquals(countUsers, users.size());
    }

    @Test
    @org.junit.jupiter.api.Order(6)
    @Transactional // for Divisioning JPQL Logs
    public void testNplusOneProblem() {
        Integer qryCount = 0;
        Integer orderCountActual = 0;

        Integer orderCountExpected = countOrders.intValue();
        Integer N = countUsers.intValue();

        List<User> users = userRepository.findAll(); qryCount++;

        for(User user : users) {
            List<Order> orders = user.getOrders();

            if(!em.getEntityManagerFactory().getPersistenceUnitUtil().isLoaded(orders)) { // proxy 상태인가?
                qryCount++;
            }

            orderCountActual += orders.size(); // proxy 해제
        }

        assertEquals(orderCountExpected, orderCountActual);
        assertEquals(N+1, qryCount); // user 수, orders 불러오는 쿼리 수가 같음
    }


    @Test
    @org.junit.jupiter.api.Order(7)
    @Transactional // for Divisioning JPQL Logs
    public void testNplusOneProblemNotSolvedYet() {
        Integer qryCount = 0;
        Integer orderCountActual = 0;

        Integer orderCountExpected = countOrders.intValue();
        Integer N = countUsers.intValue();

        List<User> users = userRepository.findAllCollectionJoinProblemSolved(); qryCount++; // orders 바로 불러옴

        for(User user : users) {
            List<Order> result = user.getOrders();

            if(!em.getEntityManagerFactory().getPersistenceUnitUtil().isLoaded(result)){
                qryCount++;
            }

            orderCountActual += result.size();
        }

        assertEquals(orderCountExpected, orderCountActual);
        assertEquals(N+1, qryCount);
    }

    @Test
    @org.junit.jupiter.api.Order(8)
    @Transactional // for Divisioning JPQL Logs
    public void testNplusOneProblemSolved() {
        Integer qryCount = 0;
        Integer orderCountActual = 0;

        Integer orderCountExpected = countOrders.intValue();
        Integer N = countUsers.intValue();

        List<User> users = userRepository.findAllCollectionJoinAndNplusOneProblemSolved(); qryCount++;

        for(User user : users) {
            List<Order> result = user.getOrders();

            if(!em.getEntityManagerFactory().getPersistenceUnitUtil().isLoaded(result)){
                qryCount++;
            }

            orderCountActual += result.size();
        }

        assertEquals(orderCountExpected, orderCountActual);
        assertEquals(1, qryCount);
    }

    @Test
    @org.junit.jupiter.api.Order(9)
    @Transactional
    @Rollback(false)
    public void testFindOrderByIdFinal() {
        List<Order> orders = userRepository.findOrdersById(users[0].getId());
        assertEquals(3, orders.size());
    }

    @Test
    @org.junit.jupiter.api.Order(100)
    @Transactional
    @Rollback(false)
    public void cleanUp() {
        for(Order order : orders) {
            orderRepository.deleteById(order.getId());
        }

        for(User user : users) {
            userRepository.deleteById(user.getId());
        }
    }
}