package hello.shop.web.Order;

import hello.shop.Entity.Item.Item;
import hello.shop.Entity.Member.Member;
import hello.shop.Entity.Order.Order;
import hello.shop.Entity.Order.OrderSearch;
import hello.shop.Sevice.Item.ItemService;
import hello.shop.Sevice.Member.MemberService;
import hello.shop.Sevice.Order.OrderService;
import hello.shop.web.Session.SessionConst;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;
    private final ItemService itemService;
    private final MemberService memberService;

    @GetMapping("/order")
    public String createForm(HttpServletRequest request, Model model) {
        //List<Member> members = memberService.findMembers();
        HttpSession session = request.getSession(false);
        if(session == null) {
            model.addAttribute("message", "로그인이 필요합니다.");
            model.addAttribute("searchUrl", "/login");
        }
        Member member = (Member) session.getAttribute(SessionConst.LOGIN_MEMBER);
        List<Item> items = itemService.findItems();

        model.addAttribute("member", member);
        model.addAttribute("items", items);

        return "order/orderForm";
    }

    @PostMapping("/order")
    public String order(HttpServletRequest request,
                        @RequestParam("itemId") Long itemId,
                        @RequestParam("count") int count,
                        Model model) {
        HttpSession session = request.getSession(false);
        if(session == null) {
            model.addAttribute("message", "로그인이 필요합니다.");
            model.addAttribute("searchUrl", "/login");
        }

        Member member = (Member) session.getAttribute(SessionConst.LOGIN_MEMBER);
        orderService.order(member.getId(), itemId, count);

        return "redirect:/orders";
    }

    @GetMapping("/orders")
    public String orderList(@ModelAttribute("orderSearch") OrderSearch orderSearch,
                            HttpServletRequest request,
                            Model model) {
        HttpSession session = request.getSession(false);
        if(session == null) {
            model.addAttribute("message", "로그인이 필요합니다.");
            model.addAttribute("searchUrl", "/login");
        }

        Member member = (Member) session.getAttribute(SessionConst.LOGIN_MEMBER);
        List<Order> orders = orderService.findOrders(orderSearch);
        List<Order> memberOrders = new ArrayList<>();

        for(Order order : orders) {
            if(order.getMember().getUsername().equals(member.getUsername())) {
                memberOrders.add(order);
            }
        }
        model.addAttribute("orders", memberOrders);

        return "order/orderList";
    }

    @PostMapping("/orders/{orderId}/cancel")
    public String cancelOrder(@PathVariable("orderId") Long orderId) {
        orderService.cancelOrder(orderId);

        return "redirect:/orders";
    }
}
