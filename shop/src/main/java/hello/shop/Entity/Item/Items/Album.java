package hello.shop.Entity.Item.Items;

import hello.shop.Entity.Item.Item;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter @Setter
@DiscriminatorValue("A")
public class Album extends Item {

    private String artist;
    private String etc;

    /*
        생성 편의 메서드
     */
    public void addBook(String artist, String etc, String name, int price, int quantity) {
        this.artist = artist;
        this.etc = etc;
        this.setName(name);
        this.setPrice(price);
        this.setStockQuantity(quantity);
    }
}
