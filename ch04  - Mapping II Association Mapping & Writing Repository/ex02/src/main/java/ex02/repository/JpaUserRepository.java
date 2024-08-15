package ex02.repository;

import ex02.domain.User;
import ex02.domain.dto.UserDto;
import ex02.domain.type.GenderType;
import ex02.domain.type.RoleType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface JpaUserRepository extends JpaRepository<User, Integer> {

    public User findByEmailAndPassword(String email, String password);

    @Query("select new ex02.domain.dto.UserDto(u.id, u.name) from User u where u.id=:id")
    public UserDto findById02(@Param("id") Integer id);

    @Modifying
    @Query("update User u set u.name=:name, u.email=:email, u.password=:password, u.gender=:gender, u.role=:role where u.id=:id")
    public Integer update(
            @Param("id") Integer id,
            @Param("name") String name,
            @Param("email") String email,
            @Param("password") String password,
            @Param("gender") GenderType gender,
            @Param("role") RoleType role);
}
