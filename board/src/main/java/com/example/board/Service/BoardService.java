package com.example.board.Service;

import com.example.board.Entity.Board;
import com.example.board.Repository.BoardRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.UUID;

@Service
public class BoardService {

    @Autowired
    private BoardRepository boardRepository;

    //글 작성
    public void boardWrite(Board board, MultipartFile file) throws Exception {
        if(file != null) { //file == null 을 대비한 예외 처리, file이 null일 경우 수정하는 과정에서 오류 발생 (NullPointer)

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
    public Page<Board> boardList(Pageable pageable) {

        return boardRepository.findAll(pageable);
    }

    //페이지 특정 글 검색
    public Page<Board> boardSearch(String searchKeyword, Pageable pageable){

        return boardRepository.findByTitleContaining(searchKeyword, pageable);
    }

    //글 상세 보기
    public Board boardView(Long id) {
        return boardRepository.findById(id).get();
    }

    //글 삭세
    public void boardDelete(Long id){
        boardRepository.deleteById(id);
    }
}
