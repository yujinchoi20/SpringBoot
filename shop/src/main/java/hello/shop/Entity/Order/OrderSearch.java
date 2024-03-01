package hello.shop.Entity.Order;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class OrderSearch {
    /**
     * 회원 이름과 주문 상태로 주문 정보 검색하기
     */

    private String memberName; //회원 이름
    private OrderStatus orderStatus; //주문 상태[ORDER, CANCEL]
}
