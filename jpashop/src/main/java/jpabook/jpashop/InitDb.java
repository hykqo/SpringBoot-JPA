package jpabook.jpashop;

import jpabook.jpashop.domain.*;
import jpabook.jpashop.domain.item.Book;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import javax.persistence.EntityManager;
/**
 * userA
 *   JPA1 BOOK
 *   JPA2 BOOK
 * userB
 *   SPRING1 BOOK
 *   SPRING2 BOOK
 * */
@Component
@RequiredArgsConstructor
public class InitDb {

    private final InitService initService;

    @PostConstruct //Spring bean이 전부 올라오고 나면 호출이 된다.
    public void init(){
        initService.dbInit1();
        initService.dbInit2();
    }


    @Component
    @Transactional
    @RequiredArgsConstructor
    static class InitService{
        private final EntityManager em;
        public void dbInit1(){
            Member member = Member.JOIN("userA", new Address("서울", "1", "1"));
            em.persist(member);

            Book book1 = Book.createBook("JPA1 BOOK", 10000, 100, "김영한", "김영한");
            em.persist(book1);
            Book book2 = Book.createBook("JPA2 BOOK", 20000, 100, "김영한", "김영한");
            em.persist(book2);

            OrderItem orderItem1 = OrderItem.createOrderItem(book1, 10000, 1);
            OrderItem orderItem2 = OrderItem.createOrderItem(book2, 20000, 1);

            Delivery delivery = new Delivery();
            delivery.setAddress(member.getAddress());
            Order order = Order.createOrder(member, delivery, orderItem1, orderItem2);
            em.persist(order);
        }

        public void dbInit2(){
            Member member = Member.JOIN("userB", new Address("경기", "1", "1"));
            em.persist(member);

            Book book1 = Book.createBook("SPRING1 BOOK", 30000, 100, "김영한", "김영한");
            em.persist(book1);
            Book book2 = Book.createBook("SPRING2 BOOK", 40000, 100, "김영한", "김영한");
            em.persist(book2);

            OrderItem orderItem1 = OrderItem.createOrderItem(book1, 30000, 1);
            OrderItem orderItem2 = OrderItem.createOrderItem(book2, 40000, 1);

            Delivery delivery = new Delivery();
            delivery.setAddress(member.getAddress());
            Order order = Order.createOrder(member, delivery, orderItem1, orderItem2);
            em.persist(order);
        }
    }
}
