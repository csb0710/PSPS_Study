package com.study1.board.service;

import com.study1.board.entity.Board;
import com.study1.board.repository.BoardRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BoardService {

    @Autowired
    private BoardRepository boardRepository;
    //글 작성 처리
    public void write(Board board){
        boardRepository.save(board);
    }
    //게시글 리스트 처리
    public List<Board> boardList(){
        return boardRepository.findAll();
    }

    public Board boardView(Integer id){

        return boardRepository.findById(id).get();
    }

    //게시글 삭제
    public void boardDelete(Integer id){
        boardRepository.deleteById(id);
    }
}
