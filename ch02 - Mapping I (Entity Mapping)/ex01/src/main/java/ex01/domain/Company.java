package ex01.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.Entity;
import javax.persistence.Id;

@Setter
@Getter
@ToString
// 1. 기본 생성자는 반드시 있어야 한다.
@NoArgsConstructor
@Entity
public class Company {
    // 2. 기본키는 반드시 지정
    @Id
    private String name;

    // 3. 저장 필드에 final 금지
    // private final String call = "000-0000-0000";
}
