package entity;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class LoginEntity {
    private String userName;
    private String email;
    private String password;
}
