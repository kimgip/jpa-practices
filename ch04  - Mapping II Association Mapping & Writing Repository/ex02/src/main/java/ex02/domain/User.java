package ex02.domain;

import ex02.domain.type.GenderType;
import ex02.domain.type.RoleType;
import lombok.*;

import javax.persistence.*;
import java.util.Date;

@Getter
@Setter
@ToString
@NoArgsConstructor
@RequiredArgsConstructor
@Entity
@Table(name = "user", schema = "webdb")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "no", nullable = false)
    private Integer id;

    @NonNull
    @Column(name = "name", nullable = false, length = 20)
    private String name;

    @NonNull
    @Column(name = "email", nullable = false, length = 200)
    private String email;

    @NonNull
    @Column(name = "password", nullable = false, length = 64)
    private String password;

    @NonNull
    @Enumerated(EnumType.STRING)
    @Column(name = "gender", nullable = false)
    private GenderType gender;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "join_date", nullable = false)
    private Date joinDate = new Date();

    @Enumerated(EnumType.STRING)
    @Column(name = "role", nullable = false)
    private RoleType role =  RoleType.USER;

}