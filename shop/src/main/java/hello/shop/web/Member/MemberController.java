package hello.shop.web.Member;

import hello.shop.Entity.Member.Address;
import hello.shop.Entity.Member.Member;
import hello.shop.Entity.Order.Order;
import hello.shop.Entity.Order.OrderSearch;
import hello.shop.Entity.Order.OrderStatus;
import hello.shop.Sevice.Member.MemberService;
import hello.shop.Sevice.Order.OrderService;
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
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequiredArgsConstructor
@Slf4j
public class MemberController {

    private final MemberService memberService;
    private final OrderService orderService;

    @GetMapping("/members/new")
    public String createForm(Model model) {
        model.addAttribute("memberForm", new MemberForm());
        return "members/createMemberForm";
    }

    @PostMapping("/members/new")
    public String create(@Valid MemberForm form, BindingResult result) {
        if(result.hasErrors()) {
            return "members/createMemberForm";
        }

        Address address = new Address(form.getCity(), form.getStreet(), form.getZipcode());
        Member member = new Member();
        member.setUsername(form.getName());
        member.setUserId(form.getUserId());
        member.setPassword(form.getPassword());
        member.setAddress(address);

        memberService.join(member);

        return "redirect:/";
    }

    @GetMapping("/members")
    public String list(Model model) {
        List<Member> members = memberService.findMembers();
        model.addAttribute("members", members);

        return "members/memberList";
    }

    @GetMapping("/members/my-page")
    public String myPage(Model model, HttpServletRequest request,
                         @ModelAttribute("orderSearch")OrderSearch orderSearch) {
        HttpSession session = request.getSession(false);

        Member member = (Member) session.getAttribute(SessionConst.LOGIN_MEMBER);
        List<Order> orders = orderService.findOrders(orderSearch);
        List<Order> memberOrders = new ArrayList<>();
        int totalPrice = 0;

        for(Order order : orders) {
            //취소 주문건은 총 주문 가격에 포함하지 않음, 자신의 주문건만 보여줌
            if(order.getStatus().equals(OrderStatus.ORDER) &&
                    order.getMember().getUsername().equals(member.getUsername())) {
                memberOrders.add(order);
                totalPrice += order.getTotalPrice();
            }
        }

        model.addAttribute("totalPrice", totalPrice);
        model.addAttribute("orders", memberOrders);
        model.addAttribute("member", member);

        return "members/memberPage";
    }
}
