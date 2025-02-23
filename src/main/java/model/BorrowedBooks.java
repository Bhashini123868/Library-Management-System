package model;

import lombok.*;

@Getter
@Setter@AllArgsConstructor
@NoArgsConstructor
@ToString
public class BorrowedBooks {
    private String BorrowedBookID;
    private String StaffID;
    private String BookID;
    private String MemberID;
    private String borroweDate;
    private String isBorrowed;

}
