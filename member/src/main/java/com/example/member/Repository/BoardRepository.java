package com.example.member.Repository;

import com.example.member.Entity.Board;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BoardRepository extends JpaRepository<Board, Long> {

//    Page<Board> findByTitleContaining(String searchKeyword, Pageable pageable);
}
