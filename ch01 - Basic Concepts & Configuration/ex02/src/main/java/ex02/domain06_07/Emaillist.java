package ex02.domain06_07;

import lombok.*;

import javax.persistence.*;

@Setter
@Getter
@ToString
@RequiredArgsConstructor
@NoArgsConstructor
@Entity
public class Emaillist {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NonNull
    @Column(nullable = false)
    private String firstName;

    @NonNull
    @Column(nullable = false)
    private String lastName;

    @NonNull
    @Column(nullable = false)
    private String email;
}
