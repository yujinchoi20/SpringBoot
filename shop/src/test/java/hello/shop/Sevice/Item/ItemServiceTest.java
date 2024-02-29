package hello.shop.Sevice.Item;

import hello.shop.Entity.Item.CategoryItem;
import hello.shop.Entity.Item.Item;
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
        book.addBook("정유정", "123", "완전한 행복", 15000, 100);


        //When
        itemService.enroll(book);
        Item findBook = itemService.findOne(book.getId());

        //Then
        assertThat(book).isEqualTo(findBook);
        log.info("Name = {}", book.getName());
        log.info("Author = {}", book.getAuthor());
        log.info("Price = {}", book.getPrice());
    }
}