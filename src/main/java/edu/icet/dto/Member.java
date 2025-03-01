package edu.icet.dto;

import lombok.*;

import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@ToString
@Getter
@Setter
@Data
public class Member {
    private String memberid;
    private String name;
    private String contact;
    private LocalDate date;
}
