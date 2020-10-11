package jpabook.jpashop;

import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by gkswndks123@naver.com on 2020-10-11
 * Blog : https://blog.naver.com/gkswndks123
 * Github : https://github.com/gatsjy
 */
// 스프링부트로 테스트를 돌릴 것이기 때문에..
// 스프링과 관련된것으로 테스트 할것임을 알려줌
@RunWith(SpringRunner.class)
@SpringBootTest
public class MemberRepositoryTest {

    @Autowired MemberRepository memberRepository;

    @Test
    @Transactional // 테스트케이스면 롤백을 시킨다.
    @Rollback(false)
    public void testMember() throws Exception {
        //given
        Member member = new Member();
        member.setUsername("memberA");

        //when
        Long savedId = memberRepository.save(member);
        Member findMember = memberRepository.find(savedId);

        //then
        Assertions.assertThat(findMember.getId()).isEqualTo(member.getId());
        Assertions.assertThat(findMember.getUsername()).isEqualTo(member.getUsername());
        Assertions.assertThat(findMember).isEqualTo(member);
        System.out.println("findMember == member"+ (findMember == member)); // 같은 영속성 컨텍스트 안에서는 아이디 값이 같으면 같은 엔티티로 인식한다.
    }

}