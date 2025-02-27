package edu.icet.entity;

import jakarta.persistence.Entity;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@ToString
@Getter
@Setter
@Data
@Entity
public class FineEntity {
    private int fineId;
    private String borrowId;
    private BigDecimal fineAmount;
    private LocalDate paymentDate;
}
