package com.example.member.Controller;

import com.example.member.Entity.Board;
import com.example.member.Service.BoardService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class BoardController {

    @Autowired
    BoardService boardService;

    @GetMapping("/member/board/write")
    public String boardWrite(Board board, String title, String content, String username,
                             HttpSession session,
                             Model model) {

        String name = session.getAttribute("loginUserId").toString();

        System.out.println("TITLE: " + title);
        System.out.println("CONTENT: " + content);
        System.out.println("MEMBER: " + name);

        model.addAttribute("username", name); //사용자 확인

        //boardService.boardWrite(board);
        return "boardWrite";
    }
}
