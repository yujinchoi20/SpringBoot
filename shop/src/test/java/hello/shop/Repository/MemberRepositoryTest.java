package hello.shop.Repository;

import hello.shop.Entity.Member.Member;
import hello.shop.Repository.Member.MemberRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest
class MemberRepositoryTest {
    @Autowired //의존성 주입
    MemberRepository memberRepository;

    @Test
    @Transactional
    public void 회원가입_조회() {
        //given
        Member member1 = new Member();
        member1.setUsername("Choi");
        Member member2 = new Member();
        member2.setUsername("Yujin");

        //when
        Long saveId = memberRepository.save(member1);
        memberRepository.save(member2);

        Member findMember1 = memberRepository.findOne(saveId);
        //Member findMember2 = memberRepository.findByName(member2.getUsername());
        List<Member> memberList = memberRepository.findAll();

        //then
        assertThat(saveId).isEqualTo(member1.getId());
        assertThat(member1).isEqualTo(findMember1);
        assertThat(memberList.size()).isEqualTo(2);
        //assertThat(findMember2.getUsername()).isEqualTo(member2.getUsername());

    }
}