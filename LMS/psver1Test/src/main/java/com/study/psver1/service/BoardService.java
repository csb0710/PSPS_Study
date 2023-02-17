package com.study.psver1.service;

import com.study.psver1.entitiy.Boardpj;
import com.study.psver1.repository.BoardRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.UUID;

@Service
public class BoardService {

    @Autowired
    private BoardRepository boardRepository;

    // 글 작성 처리
    public void write(Boardpj boardpj, MultipartFile file) throws IOException {

        String projectPath = System.getProperty("user.dir") + "/src/main/webapp";

        UUID uuid = UUID.randomUUID();

        String filename = uuid + "_" + file.getOriginalFilename();

        File saveFile = new File(projectPath, filename);

        boardpj.setFilename(filename);

        boardpj.setFilepath("/webapp/" + filename);

        file.transferTo(saveFile);

        boardRepository.save(boardpj);
    }

    // 게시글 리스트 처리
    public Page<Boardpj> boardpjList(Pageable pageable){
        return boardRepository.findAll(pageable);
    }

    public Page<Boardpj> boardSearchList(String searchKeyword, Pageable pageable){
        return boardRepository.findByTitleContaining(searchKeyword, pageable);
    }

    // 특정 게시글 불러오기
    public Boardpj boardview(Integer id){
        return boardRepository.findById(id).get();
    }

    // 특정 게시글 삭제
    public void boardDelete(Integer id){
        boardRepository.deleteById(id);
    }
}
