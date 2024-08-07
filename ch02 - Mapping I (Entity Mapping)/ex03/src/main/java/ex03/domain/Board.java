package ex03.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "board", schema = "webdb")
public class Board {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "no", nullable = false)
    private Integer id;

    @Column(name = "title", nullable = false, length = 200)
    private String title;

    @Type(type = "text")
    @Column(name = "contents", nullable = false)
    private String contents;

    @Column(name = "hit", nullable = false)
    private Integer hit;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "reg_date", nullable = false)
    private Date regDate;

    @Column(name = "g_no", nullable = false)
    private Integer groupNo;

    @Column(name = "o_no", nullable = false)
    private Integer orderNo;

    @Column(name = "depth", nullable = false)
    private Integer depth;

    @Column(name = "user_no", nullable = false)
    private Integer userNo; // !! 객체지향이 아니잖아 !! => 연관관계매핑 시간에
}