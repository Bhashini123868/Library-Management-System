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
    private String isbn;
    private String bookTitle;
    private String author;
    private Integer categoryId;
    private String availability;
}
