package hello.shop.Sevice.Member;

import hello.shop.Entity.Member.Member;
import hello.shop.Entity.Order.Order;
import hello.shop.Entity.Order.OrderItem;
import hello.shop.Entity.Order.OrderSearch;
import hello.shop.Entity.Order.OrderStatus;
import hello.shop.Sevice.Item.ItemService;
import hello.shop.Sevice.Order.OrderService;
import lombok.extern.slf4j.Slf4j;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest //해당 어노테이션이 없으면 생성자 주입 다 실패
@Transactional //테스트 실행마다 트랜잭션을 시작하고, 테스트가 끝나면 롤백(테스트 케이스에서만 롤백)
@Slf4j
class MemberServiceTest {

    @Autowired //생성자 주입
    private MemberService memberService;
    @Autowired
    private OrderService orderService;

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

    @Test
    public void 회원_주문내역() throws Exception {
        //Given
        Member member = memberService.findOne(1l);


    }
}