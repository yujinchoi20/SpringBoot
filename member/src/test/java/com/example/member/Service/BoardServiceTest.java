package com.example.member.Service;

import com.example.member.Entity.Board;
import com.example.member.Entity.Member;
import com.example.member.Repository.BoardRepository;
import com.example.member.Repository.MemberRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.concurrent.ExecutionException;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
public class BoardServiceTest {

    @Autowired
    BoardService boardService;

    @Autowired
    BoardRepository boardRepository;

    @Autowired
    MemberService memberService;

    @Autowired
    MemberRepository memberRepository;

    @Test
    public void 글목록() throws Exception {
        //Given
        Member member = new Member();
        member.setUserId("1234");
        member.setPassword("1234");
        member.setUsername("choi");

        memberService.memberJoin(member); //회원가입

        Board board = new Board();
        board.setTitle("Test!!");
        board.setContent("Test ing");
        board.setMember(member);

        //When
        boardService.boardWrite(board);
        Board findBoard = boardService.boardSearch(board.getId());

        //Then
        assertEquals(member.getUsername(), findBoard.getMember().getUsername()); //expected, actual
    }
}