package ex03.domain;

import ex03.domain.type.OrderStatus;
import lombok.*;

import javax.persistence.*;

@Getter
@Setter
@ToString
@NoArgsConstructor
@RequiredArgsConstructor
@Entity
@Table(name = "orders", schema = "bookmall")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "no", nullable = false)
    private Integer id;

    @NonNull
    @Column(name = "number", nullable = false, length = 20)
    private String number;

    @Column(name = "payment", nullable = false)
    private Integer payment = 10;

    @Column(name = "shipping", nullable = false, length = 200)
    private String shipping = "somewhere";

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private OrderStatus status = OrderStatus.입금확인중;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_no")
    private User user;

    public void setUser(User user) {
        this.user = user;

        if (!user.getOrders().contains(this)) {
            user.getOrders().add(this);
        }
    }

    @Override
    public String toString() {
        return "Order{" +
                "id=" + id +
                ", number='" + number + '\'' +
                ", payment=" + payment +
                ", shipping='" + shipping + '\'' +
                ", status=" + status +
                "}";
    }
}