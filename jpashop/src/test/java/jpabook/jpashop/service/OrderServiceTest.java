package jpabook.jpashop.service;

import jpabook.jpashop.domain.Address;
import jpabook.jpashop.domain.Member;
import jpabook.jpashop.domain.Order;
import jpabook.jpashop.domain.OrderStatus;
import jpabook.jpashop.domain.item.Book;
import jpabook.jpashop.domain.item.Item;
import jpabook.jpashop.exception.NotEnoughStockException;
import jpabook.jpashop.repository.OrderRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class OrderServiceTest {

    @Autowired EntityManager em;
    @Autowired OrderService orderService;
    @Autowired OrderRepository orderRepository;

    @Test
    public void 상품주문() throws Exception{
        // given
        Member member = getMember();
        Item book = getBook("JPA", 10000, 10);

        int count = 2;

        // when
        Long orderId = orderService.order(member.getId(), book.getId(), count);

        // then
        Order getOrder = orderRepository.findOne(orderId);
        assertEquals(OrderStatus.ORDER, getOrder.getStatus(), "주문 상태는 ORDER 이어야 한다.");
        assertEquals(1, getOrder.getOrderItems().size(), "주문한 상품 종류 수가 정확해야 한다.");
        assertEquals(10000 * count, getOrder.getTotalPrice(), "주문가격은 * 수량이다.");
        assertEquals(8, book.getStockQuantity(),"주문 수량만큼 재고가 줄어야 한다.");
    }

    @Test
    public void 주문취소() throws Exception{
        // given
        Member member = getMember();
        Item book = getBook("JPA", 10000, 10);
        int count = 2;
        Long orderId = orderService.order(member.getId(), book.getId(), count);

        // when
        orderService.cancelOrder(orderId);

        // then
        Order getOrder = orderRepository.findOne(orderId);
        assertEquals(OrderStatus.CANCEL, getOrder.getStatus(), "주문 상태는 CANCEL 이어야 한다.");
        assertEquals(10, book.getStockQuantity(),"주문 수량만큼 재고가 복구되어야 한다.");
    }

    @Test
    public void 상품주문_재고수량초과() throws Exception{
        // given
        Member member = getMember();
        Item book = getBook("JPA", 10000, 10);

        // when
        int count = 11;

        // then
        assertThrows(NotEnoughStockException.class, () -> orderService.order(member.getId(), book.getId(), count), "재고 수량 부족 예외가 발생해야 한다.");
    }

    private Book getBook(String name, int price, int stockQuantity) {
        Book book = Book.createBook(name, price, stockQuantity, "김영한", "김영한");
        em.persist(book);
        return book;
    }

    private Member getMember() {
        Address address = new Address("굴포로81", "55690", "yeom");
        Member member = Member.JOIN("인천", address);
        em.persist(member);
        return member;
    }
}