package model;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Book {
    private String BookID;
    private String ISBN;
    private String Title;
    private String Author;
    private String genre;
    private boolean availability;

}
