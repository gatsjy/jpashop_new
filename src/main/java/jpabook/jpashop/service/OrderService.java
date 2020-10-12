package jpabook.jpashop.service;

import jpabook.jpashop.repository.ItemRepository;
import jpabook.jpashop.repository.MemberRepository;
import jpabook.jpashop.repository.OrderRepository;
import jpabook.jpashop.domain.*;
import jpabook.jpashop.domain.item.Item;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by Gatsjy on 2020-10-11
 * Blog : https://blog.naver.com/gkswndks123
 * Github : https://github.com/gatsjy
 */

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;
    private final MemberRepository memberRepository;
    private final ItemRepository itemRepository;

    /**
     * 주문
     */
    // <자바스터디>
    // 예를 들어서 내가 주문을 한다고 치자
    // 아니 쉽게 처방을 내린다고 하자.
    // 의사 A 가 검사 처방 B를 내린다고 했을때.. 보통은 어떻게 진행을 하는가?
    // 일단 나의 경우에는 의사 A라는 정보는 세션 값에서 가져온다. (쿼리 날리기 싫어서 세션 값에서 가져 오는 거겠지)
    // 검사 처방 B라는 정보는 해당 환자의 처방 모든 값중에 내가 원하는 값을 가져온다 (쿼리1)
    @Transactional
    public Long order(Long memberId, Long itemId, int count){
        
        // 엔티티 조회
        Member member = memberRepository.findOne(memberId);
        Item item = itemRepository.findOne(itemId);

        // 배송정보 생성
        Delivery delivery = new Delivery();
        delivery.setAddress(member.getAddress());

        // 주문 상품을 생성
        // 생성로직을 일원화 하는 것이 중요하다. -> 어떻게 막을까? (proteced로 만든다 생성자를)
        // 좋은 설계와 유지보수는 제약하는 스타일로 내가 원하는 방식을 이끌어 내는 코딩을 해야한다.
        OrderItem orderItem = OrderItem.createOrderItem(item, item.getPrice(), count);

        // 주문을 생성
        Order order = Order.createOrder(member, delivery, orderItem);

        // 주문 저장
        orderRepository.save(order);

        return order.getId();
    }

    /**
     * 주문 취소
     */
    @Transactional
    public void cancelOrder(Long orderId){
        // 주문 엔티티 조회
        Order order = orderRepository.findOne(orderId);
        // 주문 취소
        // <자바스터디>
        // 여기서 JPA의 강점이 나온다. 아이템 재고 올리는 것을 UPDATE로직을 또 짜야한다는 것이다. -> SQL을 직접 짤 수 밖에 없다는 것이다.
        // 더티체킹(변경내역감지)를 통해서 아이템의 UPDATE를 변경해준다.
        order.cancel();
    }

    /**
     * 주문 검색
     *  <자바스터디> 에서 발표할때 예전 하이버네이트에서 동적쿼리로 검색로직 짠거랑 JPA로 검색로직짠것을 비교하는 것이 필요 할 듯
     */
    public List<Order> findOrders(OrderSearch ordersearch){
        return orderRepository.findAllByString(ordersearch);
    }
}
