package edu.icet.dto;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@ToString
@Getter
@Setter
@Data
public class User {
    private String id;
    private String username;
    private String email;
    private String password;
}
