package jpabook.jpashop.domain.item;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue("B")
@Getter
@RequiredArgsConstructor(access = AccessLevel.PROTECTED)
public class Book extends Item{

    private String author;
    private String isbn;

    public static Book createBook(String name, int price, int stockQuantity, String author, String isbn){
        Book book = new Book();
        book.setName(name);
        book.setPrice(price);
        book.setStockQuantity(stockQuantity);
        book.setAuthor(author);
        book.setIsbn(isbn);
        return book;
    }

    public static Book updateBook(Long id, String name, int price, int stockQuantity, String author, String isbn){
        Book book = new Book();
        book.setId(id);
        book.setName(name);
        book.setPrice(price);
        book.setStockQuantity(stockQuantity);
        book.setAuthor(author);
        book.setIsbn(isbn);
        return book;
    }

    private void setAuthor(String author) {
        this.author = author;
    }
    private void setIsbn(String isbn) {
        this.isbn = isbn;
    }
}
