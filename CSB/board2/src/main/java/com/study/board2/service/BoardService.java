package com.study.board2.service;

import com.study.board2.entity.Board;
import com.study.board2.repository.BoardRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class BoardService {
    @Autowired
    private BoardRepository boardRepository;

    public Page<Board> boardList(Pageable pageable) {

        return boardRepository.findAll(pageable);
    }

    public void write(Board board, MultipartFile file) throws IOException {
        String projectPath = System.getProperty("user.dir") + "/src/main/resources/static/files";

        UUID uuid = UUID.randomUUID();

        String fileName = uuid + "_" + file.getOriginalFilename();

        File saveFile = new File(projectPath, fileName);

        file.transferTo(saveFile);

        board.setFilename(fileName);

        board.setFilepath("/files/" + fileName);

        boardRepository.save(board);
    }

    public void write(Board board) {

        boardRepository.save(board);
    }

    public Integer checkHits(Integer id) {
        Board board = boardRepository.findById(id).get();

        if(board.getHits() == null) {
            board.setHits(0);
        }

        Integer temp = board.getHits();
        board.setHits(temp+1);

        this.write(board);

        return board.getHits();
    }

    public Board boardView(Integer id) {

        return boardRepository.findById(id).get();
    }

    public void removeView(Integer id) {

        boardRepository.deleteById(id);
    }

    public Page<Board> boardSearchList(String searchKeyword, Pageable pageable) {

        return boardRepository.findByTitleContaining(searchKeyword, pageable);
    }
}
