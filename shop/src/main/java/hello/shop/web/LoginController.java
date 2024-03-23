package hello.shop.web;

import hello.shop.Entity.Admin.Admin;
import hello.shop.Entity.Member.Member;
import hello.shop.Sevice.Admin.AdminService;
import hello.shop.Sevice.Member.MemberService;
import hello.shop.web.Admin.AdminLoginForm;
import hello.shop.web.Session.SessionConst;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller
@RequiredArgsConstructor
@Slf4j
public class LoginController {

    private final MemberService memberService;
    private final AdminService adminService;

    @GetMapping("/login")
    public String loginForm(Model model) {
        model.addAttribute("loginForm", new LoginForm());
        return "members/memberLogin";
    }

    @PostMapping("/login")
    public String login(@Valid LoginForm form,
                        BindingResult result,
                        HttpServletRequest request,
                        Model model) {
        if(result.hasErrors()) {
            return "members/memberLogin";
        }

        //아이디 잘못 입력시 바로 예외가 터져버리기 때문에 수정할 필요가 있음
        List<Member> members = memberService.findByUserId(form.getUserId());

        for(Member member : members) {
            if (member == null) { //아이디가 존재하지 않는다면
                result.reject("해당 아이디와 일치하는 회원이 없습니다.");
                return "members/memberLogin";
            }
            if (!member.getPassword().equals(form.getPassword())) { //비밀번호가 틀리다면
                model.addAttribute("message", "비밀번호가 일치하지 않습니다.");
                model.addAttribute("searchUrl", "/login");
                return "message";
            }

            HttpSession session = request.getSession();
            session.setAttribute(SessionConst.LOGIN_MEMBER, member);
            log.info("sessionId = {}, createTime = {}", session.getId(), session.getCreationTime());
        }

        return "redirect:/";
    }

    @GetMapping("/logout")
    public String logout(HttpServletRequest request) {
        HttpSession session = request.getSession(false);

        if(session != null) {
            session.invalidate();
        }

        return "redirect:/";
    }

    @GetMapping("/admin/login")
    public String adminLoginForm(Model model) {
        model.addAttribute("adminLoginForm", new AdminLoginForm());
        return "admin/adminLogin";
    }

    @PostMapping("/admin/login")
    public String adminLogin(@Valid AdminLoginForm form,
                             BindingResult result,
                             HttpServletRequest request,
                             Model model) {
        if(result.hasErrors()) {
            return "admin/adminLogin";
        }

        //아이디 잘못 입력시 바로 예외가 터져버리기 때문에 수정할 필요가 있음
        Admin admin = adminService.findById(form.getAdminUserId());

        if (admin == null) { //아이디가 존재하지 않는다면
            result.reject("해당 아이디와 일치하는 회원이 없습니다.");
            return "admin/adminLogin";
        }
        if (!admin.getPassword().equals(form.getPassword())) { //비밀번호가 틀리다면
            model.addAttribute("message", "비밀번호가 일치하지 않습니다.");
            model.addAttribute("searchUrl", "/login");
            return "message";
        }

        HttpSession session = request.getSession();
        session.setAttribute(SessionConst.LOGIN_MEMBER, admin);
        log.info("sessionId = {}, createTime = {}", session.getId(), session.getCreationTime());

        return "redirect:/admin";
    }

    @GetMapping("/admin/logout")
    public String adminLogout(HttpServletRequest request) {
        HttpSession session = request.getSession(false);

        if(session != null) {
            session.invalidate();
        }

        return "redirect:/";
    }
}
