package jpabook.jpashop.domain;

import jpabook.jpashop.domain.item.Item;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

import static javax.persistence.FetchType.LAZY;

/**
 * Created by Gatsjy on 2020-10-11
 * Blog : https://blog.naver.com/gkswndks123
 * Github : https://github.com/gatsjy
 */
@Entity
@Getter @Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class OrderItem {

    @Id @GeneratedValue
    @Column(name = "order_item_id")
    private Long id;

    // <자바스터디>
    // 단방향 관계이다 (OrderItem에서는 Item으로 접근할 수 있는데, Item에서는 OrderItem으로 접근할 수 없다.)
    // 아이템을 볼때 나를 주문한 OrderItem을 찾을 필요가 없다. (반대로 오더아이템 중에 어떤 아이템인지는 확인할 필요가 있다.)
    @ManyToOne(fetch = LAZY) // 일대다중에 다에 포린키가 들어간다.
    @JoinColumn(name = "item_id")
    private Item item;

    // <자바스터디>
    // 한번주문할때 여러개의 아이템을 주문하므로...
    // 연관관계의 주인이다. 그러므로 OderItem.order을 ORDER_ITEM.ORDER_ID 외래키에 매핑한다.
    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "order_id")
    private Order order;

    private int orderPrice; // 주문 가격
    private int count; // 주문 수량

    // 이렇게 해 놓으면 외부에서 생성하려 할 때 컴파일 오류가 난다.
    // 리팩토링1 -> 어노테이션 @NoArgsConstructor(access = AccessLevel.PROTECTED) 로 이 기능 대체
    // protected OrderItem(){}

    //==생성 매서드==//
    public static OrderItem createOrderItem(Item item, int orderPrice, int count){
        OrderItem orderItem = new OrderItem();
        orderItem.setItem(item);
        orderItem.setOrderPrice(orderPrice);
        orderItem.setCount(count);

        item.removeStock(count);
        return orderItem;
    }

    //==비즈니스 로직==//
    public void cancel() {
        getItem().addStock(count); // 아이템에서 재고 수량 원복
    }

    /**
     * 주문 상품 전체 가격 조회
     */
    public int getTotalPrice() {
        return getOrderPrice() * getCount();
    }
}
