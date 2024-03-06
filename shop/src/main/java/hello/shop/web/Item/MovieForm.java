package hello.shop.web.Item;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class MovieForm {
    private Long id;
    private String name;
    private int price;
    private int stockQuantity;
    private String actor;
    private String director;
}
