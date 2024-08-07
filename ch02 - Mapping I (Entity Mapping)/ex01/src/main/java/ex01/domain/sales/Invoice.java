package ex01.domain.sales;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Setter
@Getter
@ToString
@NoArgsConstructor
@Entity
@Table(name="sales_invoice")
public class Invoice {
    @Id
    String number;

    Integer qty;
    Integer total;
}
