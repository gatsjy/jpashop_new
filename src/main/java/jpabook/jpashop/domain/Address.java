package jpabook.jpashop.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Embeddable;

/**
 * Created by Gatsjy on 2020-10-11
 * Blog : https://blog.naver.com/gkswndks123
 * Github : https://github.com/gatsjy
 */
@Embeddable
@Getter
public class Address {

    private String city;
    private String street;
    private String zipcode;

    // 리플렉션이나 프록시 기술을 써야하는데 기본 생성자가 없으면 이를 사용할 수 없다.
    // protected를 하는 이유는 기본생성자로 생성하는 것을 막기 위해서 이다.
    protected Address(){
    }

    public Address(String city, String street, String zipcode) {
        this.city = city;
        this.street = street;
        this.zipcode = zipcode;
    }
}
