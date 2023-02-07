package com.study.board2.service;

import com.study.board2.entity.Board;
import com.study.board2.repository.BoardRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BoardService {
    @Autowired
    private BoardRepository boardRepository;

    public List<Board> boardList() {

        return boardRepository.findAll();
    }
}
