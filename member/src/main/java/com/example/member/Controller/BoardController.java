package com.example.member.Controller;

import com.example.member.Entity.Board;
import com.example.member.Service.BoardService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class BoardController {

    @Autowired
    BoardService boardService;

    @GetMapping("/member/board/write")
    public String boardWrite(String title, String content,
                             HttpSession session,
                             Model model,
                             Board board) {

        if(session.getAttribute("loginUserId") == null){
            model.addAttribute("message", "로그인 후 사용해주세요.");
            model.addAttribute("searchUrl", "/member/login");

            return "message";
        }

        String name = session.getAttribute("loginUserId").toString();

        System.out.println("TITLE: " + title);
        System.out.println("CONTENT: " + content);
        System.out.println("MEMBER: " + name);

        model.addAttribute("username", name); //User 확인

        //boardService.boardWrite(board); //게시글 DB에 저장
        return "boardWrite";
    }

    @GetMapping("/member/board/list")
    public String boardList(Model model, Board board) {

        List<Board> list = boardService.boardList(board);
        model.addAttribute("list", list);

        return "boardList";
    }
}
