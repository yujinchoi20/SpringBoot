package hello.shop.Repository;

import hello.shop.Entity.Member.Address;
import hello.shop.Entity.Member.Member;
import hello.shop.Repository.Member.MemberRepository;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest
@Slf4j
class MemberRepositoryTest {
    @Autowired //의존성 주입
    MemberRepository memberRepository;

    @Test
    @Transactional
    public void 회원가입_조회() {
        //given
        Address address = new Address("서울", "구로", "123");
        Member member = new Member();
        member.setUsername("최유진");
        member.setUserId("yujin");
        member.setPassword("1234");
        member.setAddress(address);

        //when
        memberRepository.save(member);
        Member findMember = memberRepository.findOne(member.getId());

        //then
        log.info("username = {}, userId = {}, password = {}", findMember.getUsername(), findMember.getUserId(), findMember.getPassword());

    }
}