package hello.shop.Sevice.Member;

import hello.shop.Entity.Member.Member;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest //해당 어노테이션이 없으면 생성자 주입 다 실패
@Transactional //테스트 실행마다 트랜잭션을 시작하고, 테스트가 끝나면 롤백(테스트 케이스에서만 롤백)
class MemberServiceTest {

    @Autowired //생성자 주입
    private MemberService memberService;

    @Test
    public void 회원가입() throws Exception {
        //Given
        Member member = new Member();
        member.setUsername("yujin");

        //When
        Long saveId = memberService.join(member);

        //Then
        assertThat(member).isEqualTo(memberService.findOne(saveId));
    }

    @Test
    public void 중복_회원() throws Exception{
        //Given
        Member member = new Member();
        member.setUsername("choi");

        //Member member1 = new Member();
        //member1.setUsername("choi");

        //When
        memberService.join(member);
        //memberService.join(member1);

        //Then
        //fail("중복 회원 예외 발생함.");
    }
}