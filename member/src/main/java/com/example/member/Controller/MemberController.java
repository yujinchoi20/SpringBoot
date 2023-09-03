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

    @GetMapping("/member/join")
    public String memberJoin(){
        return "memberJoin";
    }

    @PostMapping("/member/joinPro")
    public String memberJoinPro(Member member) {
        memberService.memberJoin(member);

        return "";
    }

    @GetMapping("/member/login")
    public String memberLogin(){
        return "memberLogin";
    }

    @PostMapping("/member/loginPro")
    public String memberLoginPro(Member member, Model model) {
        String userId = member.getUserId();
        String password = member.getPassword();
        Boolean loginCheck = false;

        List<Member> members = memberService.memberLogin();
        for(Member m : members) {
            if(m.getUserId().equals(userId) && m.getPassword().equals(password)) {
                loginCheck = true;
            }
        }

        if(loginCheck) {
            model.addAttribute("message", "로그인 완료!");
            model.addAttribute("searchUrl", "/member/home");
        } else {
            model.addAttribute("message", "로그인 실패..");
            model.addAttribute("searchUrl", "/member/login");
        }
        return "message";
    }
}
