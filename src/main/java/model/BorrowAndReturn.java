package model;

import lombok.*;

@Getter
@Setter@AllArgsConstructor
@NoArgsConstructor
@ToString
public class BorrowAndReturn {
    private String RecordID;
    private String UserID;
    private String BookID;
    private String BorrowDate;
    private String ReturnDate;
    private String Fine;

}
