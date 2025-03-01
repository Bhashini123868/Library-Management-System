package edu.icet.dto;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@ToString
@Getter
@Setter
@Data
public class Book {
    private String bookId;
    private String bookTitle;
    private String isbn;
    private String availability;
    private Integer categoryId;
    private String author;
}
