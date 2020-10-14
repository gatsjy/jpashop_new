package jpabook.jpashop.domain;

import jpabook.jpashop.domain.item.Item;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

import static javax.persistence.FetchType.*;

/**
 * Created by Gatsjy on 2020-10-11
 * Blog : https://blog.naver.com/gkswndks123
 * Github : https://github.com/gatsjy
 */
@Entity
@Getter @Setter
public class Category {

    @Id @GeneratedValue
    @Column(name = "category_id")
    private Long id;

    private String name;

    @ManyToMany
    // 중간테이블 매핑을 해주는 방법이다. 다대다를 풀어 내는 방식
    @JoinTable(name ="category_item", // 다대다를 풀어 내는 중간 테이블을 매핑
        joinColumns = @JoinColumn(name="category_id"), // 중간테이블에 있는 category_id
        inverseJoinColumns = @JoinColumn(name="item_id")) // 테이블에 item으로 들어갈 것의 매핑 id
    private List<Item> items = new ArrayList<>();

    // 내 부모니까 ManyToOne일 것이다.
    @ManyToOne(fetch = LAZY)
    @JoinColumn(name="parent_id")
    private Category parent;

    // 자식은 여러개 가질 수 있다.
    @OneToMany(mappedBy = "parent")
    private List<Category> child = new ArrayList<>();

    //== 연관관계편의 메서드==/
    public void addChildCategory(Category child){
        this.child.add(child);
        child.setParent(this);
    }
}
