package jpabook.jpashop.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Gatsjy on 2020-10-11
 * Blog : https://blog.naver.com/gkswndks123
 * Github : https://github.com/gatsjy
 */
@Entity
@Getter @Setter
public class Member {

    @Id @GeneratedValue
    @Column(name ="member_id")
    private Long id;

    private String name;

    @Embedded // 내장 타입이다.
    private Address address;

    @OneToMany(mappedBy = "member") // 하나의 회원이 여러 상품을 주문하기 때문에 // 하나 -> 여러 // 나는 연관관계의 주인이 아니라 거울임을 뜻함
    private List<Order> orders = new ArrayList<>();

}
