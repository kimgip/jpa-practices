package ex02.repository;

import ex02.domain.User;
import ex02.domain.dto.UserDto;
import ex02.domain.type.GenderType;
import ex02.domain.type.RoleType;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@Slf4j
@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class TestJpaUerRepository {
    private static final User userMock = new User("고길동", "gildong@gmail.com", "1234", GenderType.male);
    private static Long countUser;

    @Autowired
    private JpaUserRepository userRepository;

    @Test
    @Order(0)
    @Transactional
    @Rollback(false)
    public void testSave(){
        userRepository.save(userMock);
        assertNotNull(userMock.getId());

        countUser = userRepository.count();
    }

    @Test
    @Order(1)
    @Transactional // for Divisioning JPQL Logs
    public void testFindById(){
        Optional<User> optionalUser = userRepository.findById(userMock.getId());
        assertEquals("고길동", optionalUser.get().getName());
    }

    @Test
    @Order(2)
    @Transactional
    @Rollback(false)
    public void testUpdatePersisted(){
        User user = userRepository.findById(userMock.getId()).get();

        user.setName("고길동2");
        user.setPassword("4321");
    }

    @Test
    @Order(3)
    @Transactional // for Divisioning JPQL Logs
    public void testFindById02(){
        UserDto userDto = userRepository.findById02(userMock.getId());
        assertEquals("고길동2", userDto.getName());
    }

    @Test
    @Order(4)
    @Transactional
    @Rollback(false)
    public void testUpdateNotPersisted() {
        Integer count = userRepository.update(userMock.getId(), "고길동3", "gildong@gmail.com", "1234", GenderType.male, RoleType.USER);
        assertEquals(1, count);
    }

    @Test
    @Order(5)
    @Transactional // for Divisioning JPQL Logs
    public void testFindByEmailAndPassword(){
        User user = userRepository.findByEmailAndPassword("gildong@gmail.com", "1234");
        assertEquals("고길동3", user.getName());
    }

    @Test
    @Order(6)
    @Transactional
    @Rollback(false)
    public void testDelete() {
        userRepository.deleteById(userMock.getId());
        assertEquals(--countUser, userRepository.count());
    }
}