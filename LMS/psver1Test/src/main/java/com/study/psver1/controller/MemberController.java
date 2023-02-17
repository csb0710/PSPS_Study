package com.study.psver1.controller;

import com.study.psver1.dto.MemberDTO;
import com.study.psver1.service.MemberService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequiredArgsConstructor
public class MemberController {

    // 생성자 주입
    private final MemberService memberService;

    @GetMapping("/member/save")
    public String saveForm(){return "save";}

    @PostMapping("/member/save")
    public String save(@ModelAttribute MemberDTO memberDTO){

        memberService.save(memberDTO);

        return "login"; // 리턴 값 수정 필요
    }

    @GetMapping("/member/login")
    public String loginForm(){return "login";}


    @PostMapping("/member/login")
    public String loginForm(@ModelAttribute MemberDTO memberDTO,
                            HttpSession session, Model model){


       MemberDTO loginResult = memberService.login(memberDTO);
       if ( loginResult != null){
           // 성공
//           session.setAttribute("loginEmail", loginResult.getMemberEmail());
           return "redirect:/board/list";
       }else{
           // 실패
           model.addAttribute("message", "로그인 실패");
           model.addAttribute("searchUrl", "/member/login");
           return "message";
       }

    }
}
