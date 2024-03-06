package hello.shop.Sevice.Item;

import hello.shop.Entity.Item.Item;
import hello.shop.Entity.Item.Items.Album;
import hello.shop.Entity.Item.Items.Book;
import hello.shop.Entity.Item.Items.Movie;
import hello.shop.Repository.Item.ItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ItemService {

    /**
     * 비즈니스 로직
     * enroll: 상품 등록
     * findOne: 상품 하나 조회
     * findItems: 전체 상품 조회
     * update: Item, Book, Album, Movie
     */
    private final ItemRepository itemRepository;

    @Transactional
    public void saveItem(Item item) {
        itemRepository.save(item);
    }

    public Item findOne(Long itemId) {
        return itemRepository.findOne(itemId);
    }

    public List<Item> findItems() {
        return itemRepository.findAll();
    }

    @Transactional
    public void updateItem(Long id, String name, int price, int stockQuantity) {
        Item item = itemRepository.findOne(id);
        item.setName(name);
        item.setPrice(price);
        item.setStockQuantity(stockQuantity);
    }

}
