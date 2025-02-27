package edu.icet.entity;

import jakarta.persistence.Entity;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@ToString
@Getter
@Setter
@Data
@Entity
public class BookEntity {
    private String bookId;
    private String isbn;
    private String bookTitle;
    private String author;
    private Integer categoryId;
    private String availability;
}
