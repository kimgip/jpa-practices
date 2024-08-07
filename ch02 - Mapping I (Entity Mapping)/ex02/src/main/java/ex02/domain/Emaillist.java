package ex02.domain;

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
    @GeneratedValue(strategy = GenerationType.IDENTITY) // oracle일땐 SEQUENCE로
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
