package hello.shop.Entity.Item;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter @Setter
@DiscriminatorColumn(name = "dtype")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
public abstract class Item {

    @Id @GeneratedValue
    @Column(name = "item_id")
    private Long id;

    private String name; //이름
    private int price; //가격
    private int stockQuantity; //재고

//    @ManyToMany //이거 쓰면 안됨, ManyToOne + OneToMany로 변경
//    private List<Category> categories = new ArrayList<>();

    @OneToMany(mappedBy = "item")
    private List<CategoryItem> categoryItems = new ArrayList<>();

    @Enumerated(EnumType.STRING)
    private ItemType type;

    /**
     * 비즈니스 로직
     * 재고 관리 - 상품 구매: stockQuantity += quantity, 상품 반품: stockQuantity -= quantity
     */

    //반품
    public void addStock(int quantity) {
        this.stockQuantity += quantity;
    }

    //구매
    public void removeStock(int quantity) {
        int restStock = this.stockQuantity - quantity;

        if(restStock < 0) {
            throw new IllegalStateException("재고가 부족합니다. 구매 수량을 조정해주세요.");
        }

        this.stockQuantity = restStock;
    }
}
