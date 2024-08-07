package ex01.domain.marketing;

import lombok.*;

import javax.persistence.*;

@Setter
@Getter
@ToString
@NoArgsConstructor
@Entity(name="MemberMarketing")
public class Member {
    @Id
    private Integer id;
    private String firstName;
    private String lastName;
    private String email;
}
