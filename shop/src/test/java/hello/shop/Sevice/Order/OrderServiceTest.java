package hello.shop.Sevice.Order;

import hello.shop.Entity.Item.Item;
import hello.shop.Entity.Item.Items.Book;
import hello.shop.Entity.Member.Address;
import hello.shop.Entity.Member.Member;
import hello.shop.Entity.Order.Order;
import hello.shop.Entity.Order.OrderItem;
import hello.shop.Repository.Order.OrderRepository;
import hello.shop.Sevice.Item.ItemService;
import hello.shop.Sevice.Member.MemberService;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
//@Rollback(value = false)
@Slf4j
class OrderServiceTest {
    @Autowired
    private OrderService orderService;
    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private MemberService memberService;
    @Autowired
    private ItemService itemService;

    @Test
    public void 상품주문() throws Exception{
        //Given

        //회원 등록
        Member member = createMember();
        memberService.join(member);

        //상품 등록
        Book book = createBook();
        itemService.enroll(book);

        //상품 주문 수량
        int orderCount = 2;

        //When
        Long orderId = orderService.order(member.getId(), book.getId(), orderCount);
        Order getOrder = orderRepository.findOne(orderId);

        //Then
        for(OrderItem orderItem : getOrder.getOrderItems()) {
            log.info("주문한 상품은 = {}", orderItem.getItem().getName());
            log.info("상품의 가격은 = {}", orderItem.getItem().getPrice() * orderCount);
            log.info("배송 정보는 = {}, {}, {}",
                    orderItem.getOrder().getMember().getAddress().getCity(),
                    orderItem.getOrder().getMember().getAddress().getStreet(),
                    orderItem.getOrder().getMember().getAddress().getZipcode());
        }
    }

    @Test
    public void 상품주문_재고수량초과() throws Exception {
        //Given

        //회원 등록
        Member member = createMember();
        memberService.join(member);

        //상품 등록
        Item item = createBook();
        itemService.enroll(item);

        int orderCount = 11;

        //When
        orderService.order(member.getId(), item.getId(), orderCount);

        //Then
        fail("재고 부족으로 주문 실패");
    }

    @Test
    public void  주문취소() {
        //Given
        int orderCount = 5;
        //회원 등록
        Member member = createMember();
        memberService.join(member);

        //상품 등록
        Item item = createBook();
        itemService.enroll(item);

        Long orderId = orderService.order(member.getId(), item.getId(), orderCount);
        Order getOrder = orderRepository.findOne(orderId);

        log.info("주문 상태 = {}", getOrder.getStatus());
        log.info("재고 확인 = {}", item.getStockQuantity());

        //When
        orderService.cancelOrder(orderId);

        //Then
        log.info("주문 상태 = {}", getOrder.getStatus());
        log.info("재고 확인 = {}", item.getStockQuantity());
    }

    private Member createMember(){
        Member member = new Member();
        member.setUsername("회원1");
        member.setAddress(new Address("서울", "구로", "1126-35"));
        return member;
    }

    private Book createBook() {
        Book book = new Book();
        book.setName("완전한 행복");
        book.setAuthor("정유정");
        book.setPrice(15000);
        book.setStockQuantity(10);
        book.setIsbn("1010");
        return book;
    }
}