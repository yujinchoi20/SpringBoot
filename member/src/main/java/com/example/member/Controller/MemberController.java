package com.example.member.Controller;

import com.example.member.Entity.Member;
import com.example.member.Service.MemberService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller
@RequiredArgsConstructor
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
    public String memberLoginPro(Member member, Model model,
                                 HttpSession session,
                                 Boolean remember) {

        //유지 여부에 따라, 세션 만료 기간 설정하기
        System.out.println("로그인 유지 여부: " + remember);

        Member loginMember = memberService.memberFindId(member.getUserId());
        if(loginMember.getUserId().equals(member.getUserId()) &&
            loginMember.getPassword().equals(member.getPassword())) {
            //세션 확인
            session.setAttribute("loginUserId", member.getUserId());

            return "memberHome";
        } else {
            model.addAttribute("message", "아이디 혹은 비밀번호가 잘못되었습니다.");
            model.addAttribute("searchUrl", "/member/login");

            return "message";
        }
    }

    @GetMapping("/member/home")
    public String memberHome() {

        return "memberHome";
    }

    public class SessionConst {
        public static final String LOGIN_MEMBER = "loginMember";
    }
}
