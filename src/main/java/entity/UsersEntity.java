package entity;


import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString

public class UsersEntity {
    private String userID;
    private  String userName;
    private  String contact;
    private String address;
    private String MembershipDate;
}
