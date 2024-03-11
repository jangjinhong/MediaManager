package com.prog.mediamanager.service;

import com.prog.mediamanager.entity.Member;
import com.prog.mediamanager.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor // 생성자 생성(final 키워드 field만 !!!)
public class MemberService {

    private final MemberRepository memberRepository;

    @Transactional
    public Long join(Member member) {
        validateDuplicateMember(member);
        memberRepository.save(member);
        return member.getId();
    }

    private void validateDuplicateMember(Member member) {
        // '멤버의 수'로 존재여부 파악하여 예외 터뜨리는게 더 간단함 (추후 수정)
        /*
        * 동시에 2개 이상의 멤버가 같은 이름으로 join insert를 하게 되면 -> 문제 발생
        * 한 번 더 체크하자(DB MEMBER.NAME을 unique로 잡아놓기!)
        * */
        List<Member> findMembers = memberRepository.findByName(member.getName());
        if(!findMembers.isEmpty()) throw new IllegalStateException("이미 존재하는 회원입니다.");
    }

    public List<Member> findMembers() {
        return memberRepository.findAll();
    }

    public Member findOne(Long id) {
        return memberRepository.findOne(id);
    }
}
