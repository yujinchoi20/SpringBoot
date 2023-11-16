package com.example.member.Repository;

import com.example.member.Entity.Member;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class MemberRepository {

    @PersistenceContext
    private EntityManager em;

    public void save(Member member) {
        em.persist(member);
    }

    public Member findOne(Long id) {
        return em.find(Member.class, id);
    }

    public Member findById(String userId) {
        Member result = null;

        try {
            result = em.createQuery("select m from Member m where m.userId = :userId", Member.class)
                    .setParameter("userId", userId)
                    .getSingleResult();
        } catch (Exception e) {
            throw new IllegalStateException("회원정보가 잘못되었습니다.");
        } finally {
            return result;
        }
    }

    public List<Member> findByName(String username) {
        return em.createQuery("select m from Member m where m.username = :username", Member.class)
                .setParameter("username", username)
                .getResultList();
    }
}
