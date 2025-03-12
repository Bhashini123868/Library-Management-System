package edu.icet.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@ToString
@Getter
@Setter
@Data
@Entity(name = "book")
public class BookEntity {
    @Id
    private String bookId;
    private String bookTitle;
    private String isbn;
    private String availability;
    private String author;
}
