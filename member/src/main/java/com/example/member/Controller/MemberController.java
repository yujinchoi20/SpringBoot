package com.example.member.Controller;

import com.example.member.Entity.Member;
import com.example.member.Service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller
public class MemberController {

    @Autowired
    private MemberService memberService;

    @GetMapping("/")
    public String login(){
        return "main";
    }

    @GetMapping("/member/join")
    public String memberJoin(){
        return "memberJoin";
    }

    @PostMapping("/member/joinPro")
    public String memberJoinPro(Member member, Model model) {
        Long id = memberService.memberJoin(member);

        if(id != 0L) {
            model.addAttribute("message", "회원 가입 완료!");
            model.addAttribute("searchUrl", "/member/login");
        } else {
            model.addAttribute("message", "이미 존재하는 회원입니다.");
            model.addAttribute("searchUrl", "/member/join");
        }

        return "message";
    }

    @GetMapping("/member/login")
    public String memberLogin(){
        return "memberLogin";
    }

    @PostMapping("/member/loginPro")
    public String memberLoginPro(Member member, Model model) {
        String userId = member.getUserId();
        String password = member.getPassword();
        String username = "";
        Boolean loginCheck = false;

        List<Member> members = memberService.memberLogin();
        for(Member m : members) {
            if(m.getUserId().equals(userId) && m.getPassword().equals(password)) {
                loginCheck = true;
                username = m.getUsername();
            }
        }

        if(loginCheck) {
            model.addAttribute("message", username + "님 로그인 완료!");
            model.addAttribute("searchUrl", "/member/home");
        } else {
            if(userId.equals("")) {
                model.addAttribute("message", "아이디를 입력해 주세요.");
                model.addAttribute("searchUrl", "/member/login");
                return "message";
            } else if(password.equals("")) {
                model.addAttribute("message", "비밀번호를 입력해 주세요.");
                model.addAttribute("searchUrl", "/member/login");
                return "message";
            }

            model.addAttribute("message", "로그인 실패..");
            model.addAttribute("searchUrl", "/member/login");
        }

        return "message";
    }

    @GetMapping("/member/home")
    public String memberHome() {

        return "memberHome";
    }
}
