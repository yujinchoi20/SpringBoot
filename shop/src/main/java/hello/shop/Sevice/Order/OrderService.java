package hello.shop.Sevice.Order;

import hello.shop.Entity.Item.Item;
import hello.shop.Entity.Member.Member;
import hello.shop.Entity.Order.Delivery.Delivery;
import hello.shop.Entity.Order.Delivery.DeliveryStatus;
import hello.shop.Entity.Order.Order;
import hello.shop.Entity.Order.OrderItem;
import hello.shop.Entity.Order.OrderSearch;
import hello.shop.Repository.Item.ItemRepository;
import hello.shop.Repository.Member.MemberRepository;
import hello.shop.Repository.Order.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class OrderService {

    /**
     * order: 주문
     * cancelOrder: 주문 취소
     * 주문 검색
     */
    private final OrderRepository orderRepository;
    private final MemberRepository memberRepository;
    private final ItemRepository itemRepository;

    @Transactional
    public Long order(Long memberId, Long itemId, int count) {

        //Entity 조회
        Member member = memberRepository.findOne(memberId);
        Item item = itemRepository.findOne(itemId);

        //배송정보
        Delivery delivery = new Delivery();
        delivery.setAddress(member.getAddress());
        delivery.setStatus(DeliveryStatus.READY);

        //주문상품
        OrderItem orderItem = OrderItem.createOrderItem(item, item.getPrice(), count);
        Order order = Order.createOrder(member, delivery, orderItem);

        orderRepository.save(order);
        return order.getId();
    }

    @Transactional
    public void cancelOrder(Long orderId) {
        Order order = orderRepository.findOne(orderId);
        order.cancel();
    }

    public List<Order> findOrders(OrderSearch orderSearch) {
        return orderRepository.findAllByCriteria(orderSearch);
    }
}
