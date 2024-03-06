package hello.shop.Sevice.Item;

import hello.shop.Entity.Item.CategoryItem;
import hello.shop.Entity.Item.Item;
import hello.shop.Entity.Item.Items.Album;
import hello.shop.Entity.Item.Items.Book;
import lombok.extern.slf4j.Slf4j;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
@Rollback(value = false)
@Slf4j
class ItemServiceTest {

    @Autowired
    private ItemService itemService;

    @Test
    public void 상품등록() {
        //Given
        Book book = new Book();
        book = addBook("정유정", "123", "완전한 행복", 15000, 100);


        //When
        itemService.saveItem(book);
        Item findBook = itemService.findOne(book.getId());

        //Then
        assertThat(book).isEqualTo(findBook);
        log.info("Name = {}", book.getName());
        log.info("Author = {}", book.getAuthor());
        log.info("Price = {}", book.getPrice());
    }

    public Book addBook(String author, String isbn, String name, int price, int quantity) {
        Book book = new Book();

        book.setAuthor(author);
        book.setIsbn(isbn);
        book.setName(name);
        book.setPrice(price);
        book.setStockQuantity(quantity);

        return book;
    }

    @Test
    public void 상품수정() throws Exception {
        //Given
        Album album = new Album();
        album.setArtist("아이브");
        album.setEtc("걸그룹");
        album.setStockQuantity(100);
        album.setName("I AM");
        album.setPrice(30000);

        itemService.saveItem(album);

        //When
        Item item = itemService.findOne(album.getId());
        String type = item.getClass().getSimpleName();
        log.info("TYPE = {}", item.getClass().getSimpleName());

        if(type.equals("Book")) {
            log.info("Book 정보 수정");
        } else if(type.equals("Album")) {
            Album findItem = (Album) itemService.findOne(album.getId());
            itemService.updateItem(findItem.getId(), "소년", 20000, 100);
            itemService.updateAlbum(findItem.getId(), "10CM", "가수");

            //Then
            log.info("앨범 정보 = {}, {}, {}, {}, {}",
                    findItem.getName(),
                    findItem.getArtist(),
                    findItem.getEtc(),
                    findItem.getPrice(),
                    findItem.getStockQuantity());
        } else if(type.equals("Movie")) {
            log.info("Movie 정보 수정");
        }




    }
}