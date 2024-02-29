package hello.shop.Repository.Order;

import hello.shop.Entity.Order.Order;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class OrderRepository {

    /**
     * save: 상품 주문
     * findOne: 주문 내역 조회
     */

    private final EntityManager em;

    public Long save(Order order) {
        em.persist(order);
        return order.getId();
    }

    public Order findOne(Long orderId) {
        return em.find(Order.class, orderId);
    }

    //public List<Order> findAll() {}
}
