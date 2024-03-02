package hello.shop.web.Item;

import hello.shop.Entity.Item.Item;
import hello.shop.Entity.Item.Items.Book;
import hello.shop.Sevice.Item.ItemService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller
@RequiredArgsConstructor
@Slf4j
public class ItemController {

    private final ItemService itemService;

    @GetMapping("/items/new")
    public String createForm(Model model) {
        model.addAttribute("form", new BookForm());
        return "items/createItemForm";
    }

    @PostMapping("/items/new")
    public String create(BookForm form) {
        Book book = new Book();
        book.setAuthor(form.getAuthor());
        book.setIsbn(form.getIsbn());
        book.setName(form.getName());
        book.setPrice(form.getPrice());
        book.setStockQuantity(form.getStockQuantity());

        log.info("Book = {}, {}, {}, {}, {}", form.getAuthor(), form.getIsbn(), form.getName(), form.getPrice(), form.getStockQuantity());
        itemService.saveItem(book);

        return "redirect:/items";
    }

    @GetMapping("/items")
    public String list(Model model) {
        List<Item> items = itemService.findItems();
        model.addAttribute("items", items);
        return "items/itemList";
    }

    @GetMapping("/items/{itemId}/edit")
    public String updateItemForm(@PathVariable("itemId") Long itemId, Model model) {
        Item editItem = itemService.findOne(itemId);

        //DTYPE을 활용해 어떤 종류의 상품인지 알아낼 필요가 있어보임

        Book editBook = (Book) editItem;
        BookForm form = new BookForm();

        form.setId(editBook.getId());
        form.setAuthor(editBook.getAuthor());
        form.setIsbn(editBook.getIsbn());
        form.setName(editBook.getName());
        form.setPrice(editBook.getPrice());
        form.setStockQuantity(editBook.getStockQuantity());

        model.addAttribute("form", form);

        return "items/updateItemForm";
    }

    @PostMapping("/items/{itemId}/edit")
    public String updateItem(@PathVariable("itemId") Long itemId, @ModelAttribute("form") BookForm form) {
        //영속화되어 있는 엔티티에 변경 감지 기능을 사용!
        itemService.updateItem(itemId, form.getName(), form.getPrice(), form.getStockQuantity());

        /*
            org.hibernate.PersistentObjectException: detached entity passed to persist:
            오류 발생 -> PK 중복으로 인한 오류라고 예상됨.
            상품 정보를 수정하면 변경된 정보만 update 하도록 로직을 짜야됨.
            변경 감지, 더티 체킹

            하지만 파라미터로 넘어온 item 엔티티는 준영속 상태이기에 변경 감지 기능이 동작하지 않는다.
            그러면 item 엔티티를 영속 상태로 만들어서 변경 감지 기능이 동작하도록 해야 한다.

            준영속 엔티티를 수정하는 2가지 방법
            1. 변경 감지 기능 사용
            2. 병합(merge) 사용 - 병합은 모든 필드를 교체한다.

            ItemRepository의 save 메서드에 이미 있는 id일 경우 merge를 진행하는 로직을 추가한다.
            하지만 병합 방법은 null 값이 들어갈 수 있는 상황이 있기 때문에 좋은 방법은 아니다.
            즉, 엔티티가 영속화 되어있는 상태에서 변경 감지 기능을 사용하는 것을 추천한다.
         */

        return "redirect:/items";
    }
}
