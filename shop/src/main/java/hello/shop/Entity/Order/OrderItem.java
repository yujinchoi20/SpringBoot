package hello.shop.Entity.Order;

import hello.shop.Entity.Item.Item;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "order_item")
@Getter @Setter
public class OrderItem {

    @Id @GeneratedValue
    @Column(name = "order_item_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "item_id")
    private Item item;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id")
    private Order order;

    private int orderPrice; //가격
    private int count; //수량

    /*
        생성 편의 메서드
     */
    public static OrderItem createOrderItem(Item item, int orderPrice, int count) {
        OrderItem orderItem = new OrderItem();
        orderItem.setItem(item);
        orderItem.setOrderPrice(orderPrice);
        orderItem.setCount(count);

        item.removeStock(count); //재고 감소
        return orderItem;
    }

    /*
        비즈니스 로직
        주문 취소: 재고 증가, 주문 상태 변경은 Order 비즈니스 로직에서 변경함.
     */
    public void cancel() {
        getItem().addStock(count); //재고 증가
    }

    /*
        조회 로직
        주문 상품 전체 가격 조회
     */
    public int getTotalPrice() {
        return getOrderPrice() * getCount(); //가격 * 갯수
    }
}
