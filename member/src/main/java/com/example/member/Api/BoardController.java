package com.example.member.Api;

import com.example.member.Entity.Board;
import com.example.member.Entity.Member;
import com.example.member.Service.BoardService;
import com.example.member.Service.MemberService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Controller
@Slf4j
public class BoardController {

    @Autowired
    BoardService boardService;

    @Autowired
    MemberService memberService;

    //글 작성
    @GetMapping("/member/board/write")
    public String boardWriteForm() {
        return "boardWrite";
    }

    @PostMapping("/member/board/writePro")
    public String boardWritePro(Board board, Model model,
                                MultipartFile file,
                                HttpServletRequest request) throws Exception {

        String userId = null;
        Cookie[] cookies = request.getCookies();

        for(Cookie cookie : cookies) {
            log.debug("cookie {}", cookie);
            if(cookie.getName().equals("userId")) {
                userId = cookie.getValue();
                break;
            }
        }

        Member member = memberService.findById(userId);
        //member.addBoard(board);
        board.setMember(member);
        boardService.boardWrite(board, file);

        try {
            model.addAttribute("message", "글 작성 완료!");
            model.addAttribute("searchUrl", "/member/board/list");
        } catch (Exception e) {
            e.printStackTrace();
        }

        return "message";
    }

    @GetMapping("/member/board/list")
    public String boardList(Model model,
                            @PageableDefault(page = 0, size = 10, sort = "id",
                                    direction = Sort.Direction.DESC)Pageable pageable,
                            String searchKeyword) {

        /*Page<Board> list = null;

        if(searchKeyword == null) {
            list = boardService.boardList(pageable);
        } else {
            list = boardService.boardSearch(searchKeyword, pageable);
        }

        int nowPage = list.getPageable().getPageNumber() + 1;
        int startPage = Math.max(nowPage - 4, 1);
        int endPage = Math.min(nowPage + 5, list.getTotalPages());

        model.addAttribute("list", list);
        model.addAttribute("nowPage", nowPage);
        model.addAttribute("startPage", startPage);
        model.addAttribute("endPage", endPage);*/

        List<Board> list = boardService.boardList();
        model.addAttribute("list", list);

        return "boardList";
    }

    //글 상세보기
    @GetMapping("/member/board/view")
    public String boardView(Model model, Long id) {
        model.addAttribute("board", boardService.boardView(id));
        return "boardView";
    }

    //글 삭제
    @GetMapping("/member/board/delete")
    public String boardDelete(Long id) {
        boardService.boardDelete(id);
        return "redirect:/member/board/list"; //글 삭제 후 글 목록 페이지로 돌아옴
    }

    //글 수정
    @GetMapping("/member/board/modify/{id}")
    public String boardModify(@PathVariable("id") Long id, Model model) {
        model.addAttribute("board", boardService.boardView(id));
        return "boardModify";
    }

    //글 업데이트 -> 수정이랑 차이점은..??
    @PostMapping("/member/board/update/{id}")
    public String boardUpdate(@PathVariable("id") Long id,
                              Board board, Model model,
                              MultipartFile file) throws Exception {
        Board boardTemp = boardService.boardView(id);
        boardTemp.setTitle(board.getTitle());
        boardTemp.setContent(board.getContent());

        boardService.boardWrite(boardTemp, file);

        model.addAttribute("message", "글 수정 완료!(파일)");
        model.addAttribute("searchUrl", "/member/board/list");

        return "message";
    }
}
