package hello.shop.Repository.Member;

import hello.shop.Entity.Member.Member;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor //생성자 주입 + 엔티티매니저 주입 제공
public class MemberRepository {

    /**
     * save: 회원 등록
     * findOne: id로 회원 조회
     * findAll: 전체 회원 조회
     * findByName: 이름으로 회원 조회
     */
    private final EntityManager em;

    public Long save(Member member) {
        em.persist(member);
        return member.getId();
    }

    public Member findOne(Long memberId) {
        return em.find(Member.class, memberId);
    }

    public List<Member> findAll() {
        return em.createQuery("select m from Member m", Member.class)
                .getResultList();
    }

    public List<Member> findByName(String username) {
        return em.createQuery("select m from Member m where m.username =: username", Member.class)
                .setParameter("username", username)
                .getResultList();
    }

    public List<Member> findByUserId(String userId) {
        return em.createQuery("select m from Member m where m.userId =: userId", Member.class)
                .setParameter("userId", userId)
                .getResultList();
    }
}
