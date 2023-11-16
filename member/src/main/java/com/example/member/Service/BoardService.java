package com.example.member.Service;

import com.example.member.Entity.Board;
import com.example.member.Repository.BoardRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.List;
import java.util.UUID;

@Service
public class BoardService {

    @Autowired
    BoardRepository boardRepository;

    //글 작성
    @Transactional
    public void boardWrite(Board board, MultipartFile file) throws Exception {
        if(file != null) {
            String projectPath = System.getProperty("user.dir") + "\\src\\main\\resources\\static\\files";
            UUID uuid = UUID.randomUUID();
            String fileName = uuid + "_" + file.getOriginalFilename();
            File saveFile = new File(projectPath, fileName);
            file.transferTo(saveFile);

            board.setFileName(fileName);
            board.setFilePath("/files/" + fileName);
        }
        boardRepository.save(board);
    }

    //글 목록
    public List<Board> boardList() {
        return boardRepository.findAll();
    }

    //페이지 특정 글 검색
//    public Page<Board> boardSearch(String searchKeyword, Pageable pageable) {
//        return boardRepository.findByTitleContaining(searchKeyword, pageable);
//    }

    //글 상세보기
    public Board boardView(Long id) {
        return boardRepository.findById(id).get();
    }

    //글 삭제
    public void boardDelete(Long id) {
        boardRepository.deleteById(id);
    }
}
