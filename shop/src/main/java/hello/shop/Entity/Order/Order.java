package hello.shop.Entity.Order;

import hello.shop.Entity.Member.Member;
import hello.shop.Entity.Order.Delivery.Delivery;
import hello.shop.Entity.Order.Delivery.DeliveryStatus;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "orders")
@Getter @Setter
public class Order {

    @Id @GeneratedValue
    @Column(name = "order_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL)
    private List<OrderItem> orderItems = new ArrayList<>();

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "delivery_id") //order-delivery 연관관계에서는 order가 주인
    private Delivery delivery;

    private LocalDate orderDate; //주문 시간

    @Enumerated(EnumType.STRING)
    private OrderStatus status; //주문 상태 (ORDER, CANCEL)

    /*
        연관관계 메서드
        연관관계 메서드가 없으면 연관관계를 맺은 객체의 정보가 null로 저장됨.
        this <- order 객체
     */
    public void setMember(Member member) {
        this.member = member;
        member.getOrders().add(this);
    }

    public void addOrderItem(OrderItem orderItem) {
        orderItems.add(orderItem);
        orderItem.setOrder(this);
    }

    public void setDelivery(Delivery delivery) {
        this.delivery = delivery;
        delivery.setOrder(this);
    }

    /*
        생성 편의 메서드
     */
    public static Order createOrder(Member member, Delivery delivery, OrderItem... orderItems) {
        Order order = new Order();
        order.setMember(member);
        order.setDelivery(delivery);

        for(OrderItem orderItem : orderItems) {
            order.addOrderItem(orderItem);
        }

        order.setStatus(OrderStatus.ORDER);
        order.setOrderDate(LocalDate.now());

        return order;
    }

    /*
        비즈니스 로직
        주문 취소: 주문 상태에 따라 취소가 불가능할 수도 있음

        엔티티가 비즈니스 로직을 가지고 객체 지향의 특성을 적극 활용하는 도메인 모델 패턴을 사용
     */
    public void cancel() {
        if(delivery.getStatus() == DeliveryStatus.COMP) {
            throw new IllegalStateException("이미 배송완료/배송중 상태인 상품은 취소가 불가능합니다.");
        }

        this.setStatus(OrderStatus.CANCEL);
        for(OrderItem orderItem : orderItems) {
            orderItem.cancel();
        }
    }

    /*
        조회 로직
        전체 주문 가격 조회
     */
    public int getTotalPrice() {
        int totalPrice = 0;

        for(OrderItem orderItem : orderItems) {
            totalPrice += orderItem.getOrderPrice();
        }

        return totalPrice;
    }
}
