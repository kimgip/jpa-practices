package ex03.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.time.LocalDate;
import java.util.Date;

@Getter
@Setter
@Entity
@Table(name = "guestbook_log", schema = "webdb")
public class GuestbookLog {
    @Id
    @Column(name = "date", nullable = false)
    private LocalDate date; // localDate: 불변객체

    @Column(name = "count", nullable = false)
    private Integer count;
}