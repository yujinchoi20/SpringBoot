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
    public static Album createAlbum(String name, int price, int stockQuantity, String artist, String etc) {
        Album album = new Album();
        album.setName(name);
        album.setPrice(price);
        album.setStockQuantity(stockQuantity);
        album.setArtist(artist);
        album.setEtc(etc);

        return album;
    }
}
