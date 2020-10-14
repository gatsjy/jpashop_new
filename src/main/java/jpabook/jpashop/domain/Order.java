package jpabook.jpashop.domain;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static javax.persistence.FetchType.*;

/**
 * Created by Gatsjy on 2020-10-11
 * Blog : https://blog.naver.com/gkswndks123
 * Github : https://github.com/gatsjy
 * 참고 : 외래키가 있는 곳을 연관관계의 주인으로 정해라
 * 연관 관계의 주인은 단순히 외래키를 누가 관리하느냐의 문제지 비즈니스상 우위에 있다고 주인으로 정하면 안된다.
 * 예를 들어서 자동차와 바퀴가 있으면, 일대다 관계에서 항상 다쪽에 외래키가 있으므로 외래 키가 있는 바퀴를 연관관계의 주인으로 정하면 된다.
 *
 * 참고 : 엔티티 클래스 개발
 * 이론적으로 Getter Setter 모두 제공하지 않고, 꼭 필요한 별도의 메서드를 제공하는게 가장 이상적이다.
 * 하지만 실무에서 엔티티의 데이터는 조회할 일이 너무 많으므로, Getter는 열어두는 것이 편리하다.
 * Setter를 막 열어두면 가까운미래에 엔티티가 도대체 왜 변경되었는지 추적하기 점점 힘들어진다.
 * 
 * 엔티티 설계시 주의점
 * 1. 모든 연관관계는 지연로딩으로 설정
 * - 즉시 로딩(EAGER)은 예측이 어렵고, 어떤 SQL이 추적하기가 힘들다.
 * - "X"toOne 을 조심해야 한다. -> 기본 Fetch 전략이 EAGER이다
 */
@Entity
@Table(name ="orders")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter @Setter
public class Order {

    @Id @GeneratedValue
    @Column(name = "order_id")
    private Long id;

    // <자바스터디>
    // 하나의 회원은 여러 주문을 가질 수 있다(다대일) -> ManyToOne
    // Order가 Member의 외래키를 가지고 있기 때문에 연관관계의 주인이라고 표현한다 -> 그러므로 Order.member을 ORDERS.MEMBER_ID 외래키와 매핑 한다.
    // 주인 쪽의 값을 변경해야지 member의 값도 변경된다.
    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "member_id") // foreign key가 member_id 가 되는 것을 뜻함
    private Member member;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL) // order를 persist하면 orderItem도 강제적으로 persist해준다.
    private List<OrderItem> orderItems = new ArrayList<>();

    // persist(orderItemA)
    // persist(orderItemB)
    // persist(orderItemC)
    // persist(order)
    // 각각 해줘야하지만 cascade를 걸면 전부 persist를 해준다. delete할때도 같이 지워준다.

    // 연관관계의 주인은 JoinColumn을 잡아준다.
    @OneToOne(fetch = LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "delivery_id")
    private Delivery delivery;

    private LocalDateTime orderDate; // 주문시간

    @Enumerated(EnumType.STRING)
    private OrderStatus status; // 주문상태 [ORDER, CANCEL]

    //==연관관계편의 메서드(양뱡향일때 사용하는 메서드)==//
    // 양 방향을 때 원자적으로 하나로 묶는 처리
    public void setMember(Member member){
        this.member = member;
        member.getOrders().add(this);
    }

    public void addOrderItem(OrderItem orderItem){
        orderItems.add(orderItem);
        orderItem.setOrder(this);
    }

    public void setDelivery(Delivery delivery){
        this.delivery = delivery;
        delivery.setOrder(this);
    }

    //==생성 메서드==//
    // OrderItem... 자바 5에 추가된 가변인자(java varargs) 기능 -> 공부하기
    public static Order createOrder(Member member, Delivery delivery, OrderItem... orderItems){
        Order order = new Order();
        order.setMember(member);
        order.setDelivery(delivery);
        for (OrderItem orderItem : orderItems) {
            order.addOrderItem(orderItem);
        }
        order.setStatus(OrderStatus.ORDER);
        order.setOrderDate(LocalDateTime.now());
        return order;
    }

    //==비즈니스 로직==//
    /**
     * 주문 취소
     */
    public void cancel(){
        if(delivery.getStatus() == DeliveryStatus.COMP){
            throw new IllegalStateException("이미 배송완료된 상품은 취소가 불가능 합니다.");
        }
        // <자바스터디> 이 값만 바꾸고 따로 update를 한것이 없는데 자동으로 값이 변경된다.
        this.setStatus(OrderStatus.CANCEL);
        for (OrderItem orderItem : orderItems) {
            orderItem.cancel(); // 오더 아이템 마다 캔슬을 각각 날려 줘야 한다.
        }
    }

    //==조회 로직==//
    /**
     * 전체 주문 가격 조회
     */
    public int getTotalPrice() {
        //람다식으로 표현
        //return orderItems.stream().mapToInt(OrderItem::getTotalPrice).sum();
        int totalPrice = 0;
        for (OrderItem orderItem : orderItems) {
            totalPrice += orderItem.getTotalPrice();
        }
        return totalPrice;
    }
}
