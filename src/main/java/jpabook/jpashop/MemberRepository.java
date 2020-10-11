package jpabook.jpashop;

import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 * Created by gkswndks123@naver.com on 2020-10-11
 * Blog : https://blog.naver.com/gkswndks123
 * Github : https://github.com/gatsjy
 */
@Repository
public class MemberRepository {

    @PersistenceContext
    private EntityManager em; // 스프링 컨테이너에서 자동 주입해준다.

    // 설계 팁 : 커맨드와 쿼리를 분리하라.
    public Long save(Member member){
        em.persist(member);
        return member.getId();
    }

    public Member find(Long id){
        return em.find(Member.class, id);
    }

}
