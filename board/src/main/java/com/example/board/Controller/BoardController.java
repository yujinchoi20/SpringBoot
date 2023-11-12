package com.example.board.Controller;

import com.example.board.Entity.Board;
import com.example.board.Service.BoardService;
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

@Controller
public class BoardController {

    @Autowired
    private BoardService boardService;

    //글 작성
    @GetMapping("/board/write")
    public String boardWriteForm() {

        return "boardWrite";
    }

    //글 작성 완료
    @PostMapping("/board/writePro")
    public String boardWritePro(Board board, Model model, MultipartFile file) throws Exception {
        boardService.boardWrite(board, file);

        model.addAttribute("message", "글 작성 완료!");
        model.addAttribute("searchUrl", "/board/list");

        return "message";
    }

    //글 목록 + 페이징 처리
    @GetMapping("/board/list")
    public String boardList(Model model,
                            @PageableDefault(page = 0, size = 10, sort = "id", direction = Sort.Direction.DESC) Pageable pageable,
                            String searchKeyword) {

        Page<Board> list = null;

        if(searchKeyword == null) {
            list = boardService.boardList(pageable);
        } else {
            list = boardService.boardSearch(searchKeyword, pageable);
        }

        int nowPage = list.getPageable().getPageNumber() + 1;
        int startPage = Math.max(nowPage - 4,  1);
        int endPage = Math.min(nowPage + 5, list.getTotalPages());

        model.addAttribute("list", list);
        model.addAttribute("nowPage", nowPage);
        model.addAttribute("startPage", startPage);
        model.addAttribute("endPage", endPage);

        return "boardList";
    }

    //글 상세보기
    @GetMapping("/board/view")
    public String boardView(Model model, Long id) {
        model.addAttribute("board", boardService.boardView(id));
        return "boardView";
    }

    //글 삭제
    @GetMapping("/board/delete")
    public String boardDelete(Long id) {
        boardService.boardDelete(id);

        return "redirect:/board/list";
    }

    //글 수정
    @GetMapping("/board/modify/{id}")
    public String boardModify(@PathVariable("id") Long id,
                              Model model) {
        model.addAttribute("board", boardService.boardView(id));

        return "boardModify";
    }

    //여기서 덮어씌우는 방법이 아닌 변경 감지를 사용! -> 더티체킹
    @PostMapping("/board/update/{id}")
    public String boardUpdate(@PathVariable("id") Long id,
                              Board board,
                              Model model,
                              MultipartFile file) throws Exception {

        Board boardTemp = boardService.boardView(id);
        boardTemp.setTitle(board.getTitle());
        boardTemp.setContent(board.getContent());

        boardService.boardWrite(boardTemp, file);

        model.addAttribute("message", "글 수정 완료!");
        model.addAttribute("searchUrl", "/board/list");

        return "message";
    }
}
