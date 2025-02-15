package model;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Login {
    private String userName;
    private String email;
    private String password;
}
