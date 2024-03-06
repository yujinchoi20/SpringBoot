package hello.shop.web.Item;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class AlbumForm {
    private Long id;
    private String name;
    private int price;
    private int stockQuantity;
    private String artist;
    private String etc;
}
