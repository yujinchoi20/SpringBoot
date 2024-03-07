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
public static Movie createMovie(String name, int price, int stockQuantity, String director, String actor) {
        Movie movie = new Movie();
        movie.setName(name);
        movie.setPrice(price);
        movie.setStockQuantity(stockQuantity);
        movie.setDirector(director);
        movie.setActor(actor);

        return movie;
    }
}
