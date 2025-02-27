package edu.icet.entity;

import jakarta.persistence.Entity;
import lombok.*;

import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@ToString
@Getter
@Setter
@Data
@Entity
public class MemberEntity {
    private String id;
    private String name;
    private String contact;
    private LocalDate date;
}
