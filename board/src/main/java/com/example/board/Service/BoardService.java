package com.example.board.Service;

import com.example.board.Entity.Board;
import com.example.board.Repository.BoardRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.List;
import java.util.UUID;

@Service
public class BoardService {

    @Autowired
    private BoardRepository boardRepository;

    //글 작성
    public void boardWrite(Board board, MultipartFile file) throws Exception {
        String projectPath = System.getProperty("user.dir") + "\\src\\main\\resources\\static\\files";
        UUID uuid = UUID.randomUUID();
        String fileName = uuid + "_" + file.getOriginalFilename();
        File saveFile = new File(projectPath, fileName);
        file.transferTo(saveFile);

        board.setFileName(fileName);
        board.setFilePath("/files/" + fileName);

        boardRepository.save(board);
    }

    //글 목록
    public List<Board> boardList() {
        return boardRepository.findAll();
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
