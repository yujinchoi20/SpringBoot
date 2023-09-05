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
    public Long memberJoin(Member member) {
        if(validateDuplicateMember(member)) {
            memberRepository.save(member);
            return member.getId();
        } else {
            return 0L;
        }
    }

    //중복 회원 확인
    private boolean validateDuplicateMember(Member member) {
        List<Member> findMembers = memberRepository.findByName(member.getUsername());

        if(!findMembers.isEmpty()) {
            return false;
        }

        return true;
    }

    //회원 아이디로 조회
    public Member memberFindId(String userId) {

        return memberRepository.findById(userId);
    }
}
