package jpabook.jpashop.controller;

import lombok.*;

@Getter @Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BookForm {

    private Long id;
    private String name;
    private int price;
    private int stockQuantity;

    private String author;
    private String isbn;
}
