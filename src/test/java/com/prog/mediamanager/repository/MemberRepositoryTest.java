package com.prog.mediamanager.repository;

import com.prog.mediamanager.entity.Member;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.*;


@RunWith(SpringRunner.class)
@SpringBootTest
public class MemberRepositoryTest {

    @Autowired MemberRepository memberRepository;

    @Test
    @Transactional //같은 영속성 컨텍스트 안 -> 같은 1차 캐시 사용
                    // member(1차캐시 저장) == findMember(1차캐시에서 조회) -> select sql Xx
    public void save() throws Exception {
//        Member member = new Member();
//        member.setName("memberA");
//        Long memberId = memberRepository.save(member);
//        Member findMember = memberRepository.find(memberId);
//
//        assertThat(findMember.getId()).isEqualTo(member.getId());
//        assertThat(findMember.getName()).isEqualTo(member.getName());
//        assertThat(findMember).isEqualTo(member);
//
//        System.out.println(findMember);
//        System.out.println(member);
    }

    @Test
    public void find() {
    }
}