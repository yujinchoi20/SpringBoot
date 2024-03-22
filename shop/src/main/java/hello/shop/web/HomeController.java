package hello.shop.web;

import hello.shop.Entity.Admin.Admin;
import hello.shop.Entity.Member.Member;
import hello.shop.web.Session.SessionConst;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@Slf4j
public class HomeController {

    @GetMapping("/")
    public String home(HttpServletRequest request, Model model) {

        HttpSession session = request.getSession(false);
        if(session == null) {
            return "home";
        }

        Member loginMember = (Member) session.getAttribute(SessionConst.LOGIN_MEMBER);

        if(loginMember == null) {
            return "home";
        }

        model.addAttribute("member", loginMember);
        log.info("username={}", loginMember.getUsername());

        return "loginHome";
    }

    @GetMapping("/admin")
    public String adminHome(HttpServletRequest request, Model model) {
        HttpSession session = request.getSession(false);
        if(session == null) {
            return "home";
        }

        Admin loginAdmin = (Admin) session.getAttribute(SessionConst.LOGIN_MEMBER);

        if(loginAdmin == null) {
            return "home";
        }

        model.addAttribute("admin", loginAdmin);
        log.info("admin = {}", loginAdmin.getName());

        return "adminHome";
    }
}
