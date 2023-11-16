package com.example.member.Api;

import com.example.member.Entity.Member;
import com.example.member.Service.MemberService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
@RequiredArgsConstructor
@Slf4j
public class MemberController {
    private final MemberService memberService;

    @GetMapping("/member")
    public String main(){
        return "main";
    }

    /*
        로그인: userId, password 회원 인증 진행
     */
    @GetMapping("/member/login")
    public String memberLogin(){
        return "memberLogin";
    }

    @PostMapping("/member/loginPro")
    public String loginPro(Member member, Model model,
                           HttpServletResponse response) {
        String userId = member.getUserId();
        String password = member.getPassword();

        Member loginMember = memberService.findById(userId);
        log.debug("login info {}", loginMember);

        if(loginMember == null) {
            model.addAttribute("message", "로그인 실패.");
            model.addAttribute("searchUrl", "/member/login");
        } else {
            if (loginMember.getPassword().equals(password)) {
                Cookie cookie = new Cookie("userId", userId);
                response.addCookie(cookie);

                model.addAttribute("message", "로그인이 완료되었습니다.");
                model.addAttribute("searchUrl", "/member/board/list");
            } else {
                model.addAttribute("message", "로그인 실패.");
                model.addAttribute("searchUrl", "/member/login");
            }
        }
        return "message";
    }

    /*
        회원가입
     */
    @GetMapping("/member/register")
    public String register() {
        return "memberJoin";
    }

    @PostMapping("/member/registerPro")
    public String registerPro(Member member, Model model) {
        if(memberService.validateDuplicateMember(member)) {
            memberService.save(member);
            model.addAttribute("message", "회원 가입이 완료되었습니다.");
            model.addAttribute("searchUrl", "/member/login");
        } else {
            model.addAttribute("message", "중복된 회원입니다.");
            model.addAttribute("searchUrl", "/member/join");
        }

        return "message";
    }

    /*
        로그아웃
     */
    @GetMapping("/member/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "main";
    }

}
