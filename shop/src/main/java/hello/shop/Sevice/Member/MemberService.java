package hello.shop.Sevice.Member;

import hello.shop.Entity.Member.Member;
import hello.shop.Repository.Member.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true) //트랜잭션 관리, 영속성 컨텍스트, 읽기 전용 -> 약간의 성능 향상
@RequiredArgsConstructor //생성자 주입
public class MemberService {
    /**
     * save: 회원 등록 -> 비즈니스 로직이므로 중복 회원 조회 기능이 필요함
     * validateDuplicateMember: 사용자 이름으로 중복 회원 검증
     * findOne: id로 회원 조회
     * findAll: 전체 회원 조회
     * findByName: 이름으로 회원 조회
     */

    //final 키워드를 추가하면 컴파일 시점에서 memberRepository를 설정하지 않는 오류를 체크할 수 있듬
    private final MemberRepository memberRepository;

    @Transactional
    public Long join(Member member) {
        validateDuplicateMember(member); //중복 회원 검증
        memberRepository.save(member);
        return member.getId();
    }

    private void validateDuplicateMember(Member member) {
        List<Member> findMember = memberRepository.findByUserId(member.getUserId());

        if(findMember.size() != 0) {
            throw new IllegalStateException("이미 존재하는 회원입니다.");
        }

    }

    public Member findOne(Long memberId) {
        return memberRepository.findOne(memberId);
    }

    public List<Member> findMembers() {
        return memberRepository.findAll();
    }

    public List<Member> findByName(String username) {
        return memberRepository.findByName(username);
    }

    public List<Member> findByUserId(String userId) {
        return memberRepository.findByUserId(userId);
    }
}
