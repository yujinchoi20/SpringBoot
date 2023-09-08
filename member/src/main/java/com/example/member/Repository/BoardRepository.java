package com.example.member.Repository;

import com.example.member.Entity.Board;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;

@Repository
public class BoardRepository {

    @PersistenceContext
    private EntityManager em;

    public void write(Board board) {
        em.persist(board);
    }
}
