package hello.shop.Entity.Item.Items;

import hello.shop.Entity.Item.Item;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter @Setter
@DiscriminatorValue("M")
public class Movie extends Item {

    private String director;
    private String actor;

    /*
        생성 편의 메서드
     */
    public void addBook(String director, String actor, String name, int price, int quantity) {
        this.director = director;
        this.actor = actor;
        this.setName(name);
        this.setPrice(price);
        this.setStockQuantity(quantity);
    }
}
