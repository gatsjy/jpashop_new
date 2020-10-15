package jpabook.jpashop.service;

import jpabook.jpashop.repository.MemberRepository;
import jpabook.jpashop.domain.Member;
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
public class MemberService {

    private final MemberRepository memberRepository;

    /**
     * 회원 가입
     */
    @Transactional(readOnly = false) // 우선 적으로 먹힘
    public Long join(Member member){
        validationDuplicateMember(member);
        memberRepository.save(member);
        return member.getId();
    }

    /**
     * 중복 회원 인지 찾는 로직
     */
    private void validationDuplicateMember(Member member) {
        List<Member> findMembers = memberRepository.findByName(member.getName());
        if(!findMembers.isEmpty()){
            throw new IllegalStateException("이미 존재하는 회원 입니다.");
        }
    }

    /**
     * 회원 전체 조회
     */
    public List<Member> findMembers(){
        return memberRepository.findAll();
    }

    /**
     * 한 건 조회
     */
    public Member findOne(Long memberId){
        return memberRepository.findOne(memberId);
    }

    /**
     * 회원 업데이트
     * 커멘드와 쿼리를 철저하게 분리하는 마인드가 필요하다.
     */
    @Transactional
    public void update(Long id, String name) {
        Member member = memberRepository.findOne(id);
        member.setName(name);
    }
}
