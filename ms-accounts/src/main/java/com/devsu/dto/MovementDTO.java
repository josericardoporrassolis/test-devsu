package com.devsu.dto;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class MovementDTO {
    private Long movementId;
    private LocalDateTime date;
    private String movementType; // "Deposit", "Withdrawal"
    private Double value;        // Transaction amount
    private Double balance;
    private String accountNumber;
}
