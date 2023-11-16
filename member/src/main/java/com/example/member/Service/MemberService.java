package com.example.member.Service;

import com.example.member.Entity.Member;
import com.example.member.Repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;

    //회원 가입
    @Transactional
    public Long save(Member member) {
        if(validateDuplicateMember(member)) {
            memberRepository.save(member);
            return member.getId();
        } else {
            throw new IllegalStateException("존재하는 회원입니다.");
        }
    }

    //중복 회원 확인
    public boolean validateDuplicateMember(Member member) {
        List<Member> findMembers = memberRepository.findByName(member.getUsername());

        if(!findMembers.isEmpty()) {
            return false;
        }

        return true;
    }

    public Member findOne(Long id) {
        return memberRepository.findOne(id);
    }

    //회원 아이디로 조회
    public Member findById(String userId) {
        return memberRepository.findById(userId);
    }

    //회원명으로 조회
    public List<Member> findByName(String username) { //동명이인
        return memberRepository.findByName(username);
    }
}
