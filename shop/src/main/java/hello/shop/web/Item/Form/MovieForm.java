package hello.shop.web.Item.Form;

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

    /*
        생성 편의 메서드
     */
    public static MovieForm createMovieForm(Long id, String name, int price, int stockQuantity, String actor, String director) {
        MovieForm form  = new MovieForm();
        form.setId(id);
        form.setName(name);
        form.setPrice(price);
        form.setStockQuantity(stockQuantity);
        form.setActor(actor);
        form.setDirector(director);

        return form;
    }
}
