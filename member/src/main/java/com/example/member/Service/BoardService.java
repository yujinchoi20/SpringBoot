package com.example.member.Service;

import com.example.member.Entity.Board;
import com.example.member.Repository.BoardRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class BoardService {

    @Autowired
    BoardRepository boardRepository;

    @Transactional
    public Long boardWrite(Board board) {
        boardRepository.write(board);

        return board.getId();
    }
}
