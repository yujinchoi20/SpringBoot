package com.example.member.Service;

import com.example.member.Entity.Board;
import com.example.member.Entity.Member;
import com.example.member.Repository.BoardRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class BoardService {

    @Autowired
    BoardRepository boardRepository;

    //글 작성
    @Transactional
    public Long boardWrite(Board board) {
        boardRepository.write(board);

        return board.getId();
    }

    //글 목록
    public List<Board> boardList(Board board) {
        return boardRepository.list(board);
    }

    //글 조회
    public Board boardSearch(Long id) {
        return boardRepository.search(id);
    }
}
