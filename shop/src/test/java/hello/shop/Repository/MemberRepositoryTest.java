package hello.shop.Repository;

import hello.shop.Entity.Member.Member;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest
class MemberRepositoryTest {
    @Autowired //의존성 주입
    MemberRepository memberRepository;

    @Test
    @Transactional
    @Rollback(value = false)
    public void 회원가입_조회() {
        //given
        Member member = new Member();
        member.setUsername("CHOI");

        //when
        Long saveId = memberRepository.save(member);
        Member findMember = memberRepository.find(saveId);

        //then
        assertThat(findMember.getId()).isEqualTo(saveId);
        assertThat(findMember).isEqualTo(member);
    }
}