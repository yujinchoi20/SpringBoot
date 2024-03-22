package hello.shop.Repository.Order;

import hello.shop.Entity.Member.Member;
import hello.shop.Entity.Order.Order;
import hello.shop.Entity.Order.OrderSearch;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class OrderRepository {

    /**
     * save: 상품 주문
     * findOne: 주문 내역 조회
     * findByMember: 회원 주문 내역 조회
     * findAllByCriteria: 회원, 주문 상태로 주문 내역 조회
     */

    private final EntityManager em;

    public Long save(Order order) {
        if(order.getId() == null) {
            em.persist(order);
        } else {
            em.merge(order);
        }
        return order.getId();
    }

    public Order findOne(Long orderId) {
        return em.find(Order.class, orderId);
    }

    //JPQL 사용
    public List<Order> findAllByString(OrderSearch orderSearch) {
        String jpql = "select o from order o join o.member m";
        boolean isFirstCondition = true;

        if(orderSearch.getOrderStatus() != null) {
            if(isFirstCondition) {
                jpql += " where";
                isFirstCondition = false;
            } else {
                jpql += " and";
            }
            jpql += " o.status =: status";
        }

        if(orderSearch.getMemberName() != null) {
            if(isFirstCondition) {
                jpql += " where";
                isFirstCondition = false;
            } else {
                jpql += " and";
            }
            jpql += " m.name like :name";
        }

        TypedQuery<Order> query = em.createQuery(jpql, Order.class)
                .setMaxResults(1000); //최대 1000건

        if(orderSearch.getOrderStatus() != null) {
            query = query.setParameter("status", orderSearch.getOrderStatus());
        }
        if(orderSearch.getMemberName() != null) {
            query = query.setParameter("name", orderSearch.getMemberName());
        }

        return query.getResultList();
    }

    //Criteria 사용
    public List<Order> findAllByCriteria(OrderSearch orderSearch) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Order> cq = cb.createQuery(Order.class);
        Root<Order> o = cq.from(Order.class);
        Join<Order, Member> m = o.join("member", JoinType.INNER);

        List<Predicate> criteria = new ArrayList<>();

        //주문 상태 검색
        if(orderSearch.getOrderStatus() != null) {
            Predicate status = cb.equal(o.get("status"), orderSearch.getOrderStatus());
            criteria.add(status);
        }

        //회원 이름 검색
        if(StringUtils.hasText(orderSearch.getMemberName())) {
            Predicate name = cb.like(m.<String>get("username"), "%" + orderSearch.getMemberName() + "%");
            criteria.add(name);
        }

        cq.where(cb.and(criteria.toArray(new Predicate[criteria.size()])));
        TypedQuery<Order> query = em.createQuery(cq)
                .setMaxResults(1000);

        return query.getResultList();
    }
}
