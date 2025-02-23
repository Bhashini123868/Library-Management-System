package entity;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class BookEntity {
    private String BookID;
    private String ISBN;
    private String Title;
    private String Author;
    private String language;

}
