package com.study.board2.controller;

import com.study.board2.entity.Board;
import com.study.board2.service.BoardService;
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

import java.io.IOException;

@Controller
public class BoardController {
    @Autowired
    private BoardService boardService;

    @GetMapping("/board/write")
    public String boardWriteForm() {

        return "boardwrite";
    }

    @PostMapping("/board/writepro")
    public String boardWritePro(Board board, Model model, MultipartFile file) throws IOException {

        boardService.write(board, file);

        model.addAttribute("message", "글 작성이 완료되었습니다");
        model.addAttribute("searchUrl", "/board/list");

        System.out.println("작성되었습니다");

        return "message";
    }

    @GetMapping("/board/list")
    public String boardList(Model model,
                            @PageableDefault(page = 0, size = 10, sort = "id", direction = Sort.Direction.DESC) Pageable pagebale,
                            String searchKeyword) {

        Page<Board> list = null;

        if(searchKeyword != null) {
            System.out.println(searchKeyword);
            list = boardService.boardSearchList(searchKeyword, pagebale);
        }
        else {
            list = boardService.boardList(pagebale);
        }



        int nowPage = list.getPageable().getPageNumber() + 1;
        int startPage = Math.max(1, nowPage - 4);
        int endPage = Math.min(list.getTotalPages()+1, nowPage + 5);

        model.addAttribute("list", list);
        model.addAttribute("nowPage", nowPage);
        model.addAttribute("startPage", startPage);
        model.addAttribute("endPage", endPage);

        return "boardlist";
    }

    @GetMapping("/board/view")
    public String boardView(Model model, Integer id) {
        boardService.checkHits(id);
        model.addAttribute("board", boardService.boardView(id));

        return "boardview";
    }

    @PostMapping("/board/remove")
    public String boardRemove(Integer id, Board board) {
        String tempPassword = boardService.boardView(id).getPassword();

        if(!tempPassword.equals(board.getPassword())) {
            return "wrongpassword";
        }

        boardService.removeView(id);

        return "redirect:/board/list";
    }

    @GetMapping("/board/checkmodi/{id}")
    public String checkModify(@PathVariable("id") Integer id, Model model) {
        model.addAttribute("board", boardService.boardView(id));

        return "checkmodify";
    }

    @GetMapping("/board/checkdel/{id}")
    public String checkDelete(@PathVariable("id") Integer id, Model model) {
        model.addAttribute("board", boardService.boardView(id));
        System.out.println(id);

        return "checkdelete";
    }

    @PostMapping("/board/modify/{id}")
    public String boardModify(@PathVariable("id") Integer id, Model model, Board board) {
        String tempPassword = boardService.boardView(id).getPassword();

        if(!tempPassword.equals(board.getPassword())) {
            return "wrongpassword";
        }

        model.addAttribute("board", boardService.boardView(id));

        return "boardmodify";
    }

    @PostMapping("/board/update/{id}")
    public String boardUpdate(@PathVariable("id") Integer id, Model model, Board board, MultipartFile file) throws IOException {
        Board boardTemp = boardService.boardView(id);

        boardTemp.setTitle(board.getTitle());
        boardTemp.setContent(board.getContent());

        boardService.write(boardTemp, file);

        model.addAttribute("message", "글 작성이 완료되었습니다");
        model.addAttribute("searchUrl", "/board/list");

        System.out.println("작성되었습니다");

        return "message";
    }

}
