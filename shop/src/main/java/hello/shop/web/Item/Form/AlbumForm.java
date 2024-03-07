package hello.shop.web.Item.Form;

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

    /*
        생성 편의 메서드
     */
    public static AlbumForm createAlbumForm(Long id, String name, int price, int stockQuantity, String artist, String etc) {
        AlbumForm form = new AlbumForm();
        form.setId(id);
        form.setName(name);
        form.setPrice(price);
        form.setStockQuantity(stockQuantity);
        form.setArtist(artist);
        form.setEtc(etc);

        return form;
    }
}
