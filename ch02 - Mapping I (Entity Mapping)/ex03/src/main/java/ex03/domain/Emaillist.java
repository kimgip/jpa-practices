package ex03.domain;

import lombok.*;

import javax.persistence.*;

@Getter
@Setter
@RequiredArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "emaillist", schema = "webdb")
public class Emaillist {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "no", nullable = false)
    private Integer id;

    @NonNull
    @Column(name = "first_name", nullable = false, length = 10)
    private String firstName;

    @NonNull
    @Column(name = "last_name", nullable = false, length = 20)
    private String lastName;

    @NonNull
    @Column(name = "email", nullable = false, length = 200)
    private String email;
}