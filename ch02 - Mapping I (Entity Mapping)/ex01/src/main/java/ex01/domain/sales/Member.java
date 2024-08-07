package ex01.domain.sales;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.Entity;
import javax.persistence.Id;

@Setter
@Getter
@ToString
@NoArgsConstructor
@Entity(name="MemberSales")
public class Member {
    @Id
    private Integer id;
    private String firstName;
    private String lastName;
    private String email;
}
