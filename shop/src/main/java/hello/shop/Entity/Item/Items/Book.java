package hello.shop.Entity.Item.Items;

import hello.shop.Entity.Item.Item;
import hello.shop.Entity.Item.ItemType;
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
    public static Book createBook(String name, int price, int stockQuantity, String author, String isbn) {
        Book book = new Book();
        book.setName(name);
        book.setPrice(price);
        book.setStockQuantity(stockQuantity);
        book.setAuthor(author);
        book.setIsbn(isbn);
        book.setType(ItemType.Book);

        return book;
    }
}
