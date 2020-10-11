package jpabook.jpashop.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Gatsjy on 2020-10-11
 * Blog : https://blog.naver.com/gkswndks123
 * Github : https://github.com/gatsjy
 */
@Entity
@Table(name ="orders")
@Getter @Setter
public class Order {

    @Id @GeneratedValue
    @Column(name = "id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "member_id") // foreign key가 member_id 가 되는 것을 뜻함
    private Member member;

    private List<OrderItem> orderItems = new ArrayList<>();

    private Delivery delivery;

    private LocalDateTime orderDate; // 주문시간

    private OrderStatus status; // 주문상태 [ORDER, CANCEL]

}
