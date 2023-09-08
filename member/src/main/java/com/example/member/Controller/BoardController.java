package com.example.member.Controller;

import com.example.member.Entity.Board;
import com.example.member.Service.BoardService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class BoardController {

    @Autowired
    BoardService boardService;

    @GetMapping("/member/board/write")
    public String boardWrite(Board board, String title, String content,
                             HttpSession session) {

        String name = session.getAttribute("loginUserId").toString();

        System.out.println("TITLE: " + title);
        System.out.println("CONTENT: " + content);
        System.out.println("Session: " + name);

        //boardService.boardWrite(board);
        return "boardWrite";
    }
}
