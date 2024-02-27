package hello.shop.Repository;

import hello.shop.Entity.Member.Member;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor //생성자 주입 + 엔티티매니저 주입 제공
public class MemberRepository {

    /**
     * save, find
     */
    private final EntityManager em;

    public Long save(Member member) {
        em.persist(member);
        return member.getId();
    }

    public Member find(Long memberId) {
        return em.find(Member.class, memberId);
    }
}
