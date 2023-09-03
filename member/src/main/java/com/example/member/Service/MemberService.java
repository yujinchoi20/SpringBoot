package com.example.member.Service;

import com.example.member.Entity.Member;
import com.example.member.Repository.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class MemberService {

    @Autowired
    private MemberRepository memberRepository;

    //회원 가입
    public void memberJoin(Member member){
        memberRepository.save(member);
    }

    //로그인
    public List<Member> memberLogin() {
        return memberRepository.findAll();
    }

}
