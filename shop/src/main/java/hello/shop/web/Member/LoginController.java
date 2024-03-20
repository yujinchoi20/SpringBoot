package hello.shop.web.Member;

import hello.shop.Entity.Member.Member;
import hello.shop.Sevice.Member.MemberService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@RequiredArgsConstructor
@Slf4j
public class LoginController {

    private final MemberService memberService;

    @GetMapping("/login")
    public String loginForm(Model model) {
        model.addAttribute("loginForm", new LoginForm());
        return "members/memberLogin";
    }

    @PostMapping("/login")
    public String login(@Valid LoginForm form, BindingResult result,
                        HttpServletRequest request) {
        if(result.hasErrors()) {
            return "members/memberLogin";
        }

        Member member = memberService.findByUserId(form.getUserId());

        if(member == null) { //아이디가 존재하지 않는다면
            result.reject("해당 아이디와 일치하는 회원이 없습니다.");
            return "members/memberLogin";
        }
        if(!member.getPassword().equals(form.getPassword())) { //비밀번호가 틀리다면
            result.reject("비밀번호가 일치하지 않습니다.");
            return "members/memberLogin";
        }

        HttpSession session = request.getSession();
        session.setAttribute(SessionConst.LOGIN_MEMBER, member);

        log.info("sessionId = {}, createTime = {}", session.getId(), session.getCreationTime());
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
}
