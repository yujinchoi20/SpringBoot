package com.example.member.Service;

import com.example.member.Entity.Member;
import com.example.member.Repository.MemberRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
public class MemberServiceTest {

    @Autowired
    MemberService memberService;
    @Autowired
    MemberRepository memberRepository;

    @Test
    public void 회원가입() throws Exception {
        //Given
        Member member = new Member();
        member.setUsername("Choi");

        //When
        Long saveId = memberService.memberJoin(member);

        //Then
        assertEquals(member, memberRepository.findOne(saveId));
    }

    @Test
    public void 중복회원() throws Exception {
        //Given
        Member member1 = new Member();
        member1.setUsername("Choi");

        Member member2 = new Member();
        member2.setUsername("Choi");

        //When
        memberService.memberJoin(member1);
        memberService.memberJoin(member2);

        //Then
        fail("예외 발생!");
    }
}