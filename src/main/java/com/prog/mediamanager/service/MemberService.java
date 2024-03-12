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

    @Transactional
    public void update(Long id, String name) {
        // dirty checking
        Member member = memberRepository.findOne(id);
        member.setName(name);
        /* update()에서 Member를 반환하면 결국 멤버를 쿼리하는 꼴이 됨
        * 커맨드 업데이트: 엔티티 변경을 수행하는 변경성 메서드
        * 때문에, Member update()는 파라미터(id)를 가지고 엔티티 조회하는 그림이 그려진다
        * 커맨드 + 쿼리가 한 메서드에 공존하므로 이를 소단위로 나눠서 관리하는 것이 좋다!
        * */
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
