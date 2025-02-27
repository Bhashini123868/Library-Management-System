package edu.icet.dto;

import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@ToString
@Getter
@Setter
@Data
public class Fine {
    private int fineId;
    private String borrowId;
    private BigDecimal fineAmount;
    private LocalDate paymentDate;
}
