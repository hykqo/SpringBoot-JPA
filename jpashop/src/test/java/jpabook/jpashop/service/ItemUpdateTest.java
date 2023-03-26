package jpabook.jpashop.service;

import jpabook.jpashop.domain.item.Book;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.persistence.EntityManager;

@SpringBootTest
public class ItemUpdateTest {

    @Autowired
    EntityManager em;

    @Test
    public void ItemUpdateTest() throws Exception{
        // given
        Book book = em.find(Book.class, 1L);
        // when
//        book.setAuthor("asd");
        // then
        //영속화된 상태일경우 dirty checking - 변경감지를 해서 데이터를 바꿔줌,
    }
}
