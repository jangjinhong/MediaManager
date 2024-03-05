package com.prog.mediamanager.service;

import com.prog.mediamanager.entity.Member;
import com.prog.mediamanager.repository.MemberRepository;
import jakarta.persistence.EntityManager;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;


@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional //commit --x, rollback
public class MemberServiceTest {

    @Autowired MemberService memberService;
    @Autowired MemberRepository memberRepository;
    @Autowired EntityManager em;

    @Test
    public void 회원가입() throws Exception {
        // given
        Member member = new Member();
        member.setName("memberA");

        // when
        Long join = memberService.join(member);
        System.out.println("member = " + member.getName());

        // then
        assertEquals(member, memberRepository.findOne(join));
    }


    @Test(expected = IllegalStateException.class)
    public void 중복_회원_예외() throws Exception {
        // given
        Member member1 = new Member();
        member1.setName("memberA");
        Member member2 = new Member();
        member2.setName("memberA");

        // when
        memberService.join(member1);
        memberService.join(member2); //exception

        // then
        fail();
    }

}