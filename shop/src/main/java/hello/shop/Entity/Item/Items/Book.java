package hello.shop.Entity.Item.Items;

import hello.shop.Entity.Item.Item;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter @Setter
@DiscriminatorValue("B")
public class Book extends Item {

    private String author;
    private String isbn;

    /*
        생성 편의 메서드
     */
    public void addBook(String author, String isbn, String name, int price, int quantity) {
        this.author = author;
        this.isbn = isbn;
        this.setName(name);
        this.setPrice(price);
        this.setStockQuantity(quantity);
    }
}
