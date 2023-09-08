package com.example.member.Controller;

import com.example.member.Entity.Member;
import com.example.member.Service.MemberService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.Banner;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller
@RequiredArgsConstructor
@Slf4j
public class MemberController {

    @Autowired
    private MemberService memberService;

    @GetMapping("/member")
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
                                 HttpServletResponse response,
                                 Boolean remember) {

        //유지 여부에 따라, 세션 만료 기간 설정하기
        System.out.println("로그인 유지 여부: " + remember);

        Member loginMember = memberService.memberFindId(member.getUserId());
        if(loginMember.getUserId().equals(member.getUserId()) &&
            loginMember.getPassword().equals(member.getPassword())) {

            //로그인 유지를 원할 경우 쿠키 생성
            if(remember != null) {
                Cookie cookie = new Cookie("userId", member.getUserId());
                cookie.setMaxAge(60*60*24); //쿠키 유지 시간: 1일
                cookie.setPath("/");

                response.addCookie(cookie);
            }

            //세션 생성
            session.setAttribute("loginUserId", member.getUserId());

            //로그인 상태 체크 (필요 없는 부분)
            String sessionUserId = (String)session.getAttribute("loginUserId");
            boolean loginInfo = sessionUserId == null ? false : true;

            if(loginInfo) {
                System.out.println("로그인 상태");
            } else {
                System.out.println("로그아웃 상태");
            }

            String name = session.getAttribute("loginUserId").toString();
            model.addAttribute("username", name);

            return "boardWrite";
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

    @GetMapping("/member/logout")
    public String memberLogout(HttpSession session, Model model) {
        session.invalidate();

        model.addAttribute("message", "로그아웃 상태");
        model.addAttribute("searchUrl", "/member");

        System.out.println("로그아웃 상태");

        return "message";
    }
}
