package com.example.member.Service;

import com.example.member.Entity.Member;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class MemberServiceTest {

    @Autowired MemberService memberService;

    @Test
    public void 회원가입() throws Exception {
        //Given
        Member member = new Member();
        member.setUserId("test");
        member.setPassword("123");
        member.setUsername("choi");

        //When
        memberService.save(member);
        Member findOne = memberService.findOne(member.getId());

        //Then
        System.out.println("member = " + findOne.getUsername());
    }

    @Test
    public void 아이디_조회() throws Exception {
        //Given
        Member member = new Member();
        member.setUserId("test");
        member.setPassword("123");
        member.setUsername("choi");
        memberService.save(member);

        //When
        Member findById = memberService.findById(member.getUserId());

        //Then
        System.out.println("userId = " + findById.getUserId());
    }

    @Test
    public void 이름_조회() throws Exception {
        //Given
        Member member = new Member();
        member.setUserId("test");
        member.setPassword("123");
        member.setUsername("choi");
        memberService.save(member);

        //When
        List<Member> findByName = memberService.findByName(member.getUsername());

        //Then
        for(Member m : findByName) {
            System.out.println("userId = " + m.getUsername());
        }
    }
}