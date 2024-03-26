package hello.shop.web.Item;

import hello.shop.Entity.Item.Item;
import hello.shop.Entity.Item.ItemSearch;
import hello.shop.Entity.Item.ItemType;
import hello.shop.Entity.Item.Items.Album;
import hello.shop.Entity.Item.Items.Book;
import hello.shop.Entity.Item.Items.Movie;
import hello.shop.Sevice.Item.ItemService;
import hello.shop.web.Item.Form.AlbumForm;
import hello.shop.web.Item.Form.BookForm;
import hello.shop.web.Item.Form.MovieForm;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;
import java.util.Set;

@Controller
@RequiredArgsConstructor
@Slf4j
public class ItemController {

    /**
     * Item 추가 시 어떤 종류의 상품인지 카테고리를 설정하여 추가하는 방법으로 변경할 것
     * 1. item/new 매핑 전에 종류를 선택하는 페이지 추가
     * 2. Book, Album, Movie 3가지 버튼 생성 -> items/new
     * 3. items/new/book, items/new/album, items/new/movie
     * 4. 종류에 따라 입력해야 하는 필드가 다름
     * 5. 종류에 따라 Item을 등록하고 목록도 종류별로 조회할 수 있도록 구현하기
     *
     */
    private final ItemService itemService;

    @GetMapping("/items/new")
    public String selectCategory() {
        return "items/selectItemCategory";
    }

    //Book 아이템 추가
    @GetMapping("/items/new/book")
    public String createBookForm(Model model) {
        model.addAttribute("form", new BookForm());
        return "items/createBookForm";
    }

    @PostMapping("/items/new/book")
    public String createItemBook(BookForm form) {
        Book book = Book.createBook(form.getName(), form.getPrice(), form.getStockQuantity(),
                form.getAuthor(), form.getIsbn());

        //log.info("Book = {}, {}, {}, {}, {}", form.getAuthor(), form.getIsbn(), form.getName(), form.getPrice(), form.getStockQuantity());
        itemService.saveItem(book);

        return "redirect:/admin/items";
    }

    //Album 아이템 추가
    @GetMapping("/items/new/album")
    public String createAlbumForm(Model model) {
        model.addAttribute("form", new AlbumForm());
        return "items/createAlbumForm";
    }

    @PostMapping("/items/new/album")
    public String createItemAlbum(AlbumForm form) {
        Album album = Album.createAlbum(form.getName(), form.getPrice(), form.getStockQuantity(),
                form.getArtist(), form.getEtc());

        //log.info("Album = {}, {}, {}, {}, {}", form.getArtist(), form.getEtc(), form.getName(), form.getPrice(), form.getStockQuantity());
        itemService.saveItem(album);

        return "redirect:/admin/items";
    }

    //movie 아이템 추가
    @GetMapping("/items/new/movie")
    public String createMovieForm(Model model) {
        model.addAttribute("form", new MovieForm());
        return "items/createMovieForm";
    }

    @PostMapping("/items/new/movie")
    public String createItemMovie(MovieForm form) {
        Movie movie = Movie.createMovie(form.getName(), form.getPrice(), form.getStockQuantity(),
                form.getDirector(), form.getActor());

        //log.info("Movie = {}, {}, {}, {}, {}", form.getActor(), form.getDirector(), form.getName(), form.getPrice(), form.getStockQuantity());
        itemService.saveItem(movie);

        return "redirect:/admin/items";
    }

    @GetMapping("/items")
    public String itemList(@ModelAttribute("itemSearch") ItemSearch itemSearch, Model model) {
        //log.info("Type = {}", itemSearch.getItemType());
        List<Item> items = itemService.findType(itemSearch.getItemType());

        model.addAttribute("items", items);
        return "items/itemList";
    }

    @GetMapping("/admin/items")
    public String adminItemList(@ModelAttribute("itemSearch") ItemSearch itemSearch, Model model) {
        //log.info("Type = {}", itemSearch.getItemType());
        List<Item> items = itemService.findType(itemSearch.getItemType());

        model.addAttribute("items", items);
        return "admin/adminItemList";
    }

    @GetMapping("/items/{itemId}/edit")
    public String updateItemForm(@PathVariable("itemId") Long itemId, Model model) {
        Item editItem = itemService.findOne(itemId);

        //log.info("Item Type = {}", editItem.getClass().getSimpleName());
        String type = editItem.getClass().getSimpleName();
        String updateItemForm = "";

        if(type.equals("Book")) {
            Book editBook = (Book) editItem;
            BookForm form = BookForm.createBookForm(editBook.getId(), editBook.getName(), editBook.getPrice(),
                    editBook.getStockQuantity(), editBook.getAuthor(), editBook.getIsbn());

            model.addAttribute("form", form);
            updateItemForm =  "updateBookForm";
        } else if(type.equals("Album")) {
            Album editAlbum = (Album) editItem;
            AlbumForm form = AlbumForm.createAlbumForm(editAlbum.getId(), editAlbum.getName(), editAlbum.getPrice(),
                    editAlbum.getStockQuantity(), editAlbum.getArtist(), editAlbum.getEtc());

            model.addAttribute("form", form);
            updateItemForm = "updateAlbumForm";
        } else if(type.equals("Movie")) {
            Movie editMovie = (Movie) editItem;
            MovieForm form = MovieForm.createMovieForm(editMovie.getId(), editMovie.getName(), editMovie.getPrice(),
                    editMovie.getStockQuantity(), editMovie.getActor(), editMovie.getDirector());

            model.addAttribute("form", form);
            updateItemForm = "updateMovieForm";
        }

        return "items/" + updateItemForm;
    }

    @PostMapping("/items/{itemId}/edit")
    public String updateItem(@PathVariable("itemId") Long itemId,
                             @ModelAttribute("bookForm") BookForm bookForm,
                             @ModelAttribute("albumForm") AlbumForm albumForm,
                             @ModelAttribute("movieForm") MovieForm movieForm) {
        //영속화되어 있는 엔티티에 변경 감지 기능을 사용!
        Item findItem = itemService.findOne(itemId);
        String type = findItem.getClass().getSimpleName();

        if(type.equals("Book")) {
            itemService.updateItem(itemId, bookForm.getName(), bookForm.getPrice(), bookForm.getStockQuantity());
            itemService.updateBook(itemId, bookForm.getAuthor(), bookForm.getIsbn());
        } else if(type.equals("Album")) {
            itemService.updateItem(itemId, albumForm.getName(), albumForm.getPrice(), albumForm.getStockQuantity());
            itemService.updateAlbum(itemId, albumForm.getArtist(), albumForm.getEtc());
        } else if(type.equals("Movie")) {
            itemService.updateItem(itemId, movieForm.getName(), movieForm.getPrice(), movieForm.getStockQuantity());
            itemService.updateMovie(itemId, movieForm.getActor(), movieForm.getDirector());
        }


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
