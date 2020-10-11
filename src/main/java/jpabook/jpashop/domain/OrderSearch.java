package jpabook.jpashop.domain;

import lombok.Getter;
import lombok.Setter;

/**
 * @author Gatsjy
 * @since 2020-10-11
 * realize dreams myself
 * Blog : https://blog.naver.com/gkswndks123
 * Github : https://github.com/gatsjy
 */
@Getter @Setter
public class OrderSearch {

    private String memberName;
    private OrderStatus orderStatus; // 주문 상태 [ORDER, CANCEL]
}
