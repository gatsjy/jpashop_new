package jpabook.jpashop.controller;

import lombok.Getter;
import lombok.Setter;

/**
 * @author Gatsjy
 * @since 2020-10-11
 * realize dreams myself
 * Blog : https://blog.naver.com/gkswndks123
 * Github : https://github.com/gatsjy
 */
@Getter
@Setter
public class BookForm {

    private Long id;
    private String name;
    private int price;
    private int stockQuantity;
    private String author;
    private String isbn;
}
