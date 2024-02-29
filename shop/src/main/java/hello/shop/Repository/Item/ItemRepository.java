package hello.shop.Repository.Item;

import hello.shop.Entity.Item.Item;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor //생성자 주입
public class ItemRepository {

    /**
     * save: 상품 등록
     * findOne, findAll: 상품 목록 조회 - 하나, 전체
     * update: 상품 정보 수정 - 변경 감지(더티 체킹)
     */

    private final EntityManager em;

    //상품 등록
    public Long save(Item item) {
        em.persist(item);
        return item.getId();
    }

    //상품 하나 조회
    public Item findOne(Long itemId) {
        return em.find(Item.class, itemId);
    }

    //전체 상품 조회
    public List<Item> findAll() {
        return em.createQuery("select i from Item i", Item.class)
                .getResultList();
    }
}
