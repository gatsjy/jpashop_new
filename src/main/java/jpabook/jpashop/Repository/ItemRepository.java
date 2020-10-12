package jpabook.jpashop.Repository;

import jpabook.jpashop.domain.item.Item;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;

/**
 * Created by Gatsjy on 2020-10-11
 * Blog : https://blog.naver.com/gkswndks123
 * Github : https://github.com/gatsjy
 */

@Repository
@RequiredArgsConstructor
public class ItemRepository {

    private final EntityManager em;

    /**
     * 아이템 저장 하는 로직
     * @param item
     */
    public void save(Item item){
        if(item.getId() == null){
            em.persist(item); // 새로 저장 하는 로직
        }else{
            em.merge(item); // 이미 등록된 것을 가져옴 (update 느낌) -> 데이터베이스에서 아이템을 찾는다. 그 다음에 파라미터로 넘어온 값으로 찾아온 것의 값을 바꿔치기 한다.
        }
    }

    /**
     * 하나의 아이템 가져 오기
     * @param id
     * @return
     */
    public Item findOne(Long id){
        return em.find(Item.class, id);
    }

    /**
     * 모든 아이템 가져 오기
     * @return
     */
    public List<Item> findAll(){
        return em.createQuery("select i from Item i", Item.class)
                .getResultList();
    }
}
