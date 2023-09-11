package com.example.member.Repository;

import com.example.member.Entity.Board;
import com.example.member.Entity.Member;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class BoardRepository {

    @PersistenceContext
    private EntityManager em;

    //글 작성
    public void write(Board board) {
        em.persist(board);
    }

    //글 목록
    public List<Board> list(Board board) {
        return em.createQuery("select b from Board b")
                .getResultList();
    }

    //글 조회
    public Board search(Long id) {
        return em.createQuery("select b from Board b where b.id=:id", Board.class)
                .setParameter("id", id)
                .getSingleResult();
    }
}
