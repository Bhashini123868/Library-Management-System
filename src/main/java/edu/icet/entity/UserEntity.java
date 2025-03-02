package edu.icet.entity;

import jakarta.persistence.*;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@ToString
@Getter
@Setter
@Data
@Entity
public class UserEntity {
    @Id
    private String id;
    private String email;
    private String password;
    private String username;
}
