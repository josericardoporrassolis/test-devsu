package com.devsu.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class ReportDTO {
    private LocalDateTime date;
    private String client;
    private String accountNumber;
    private String type;
    private Double initialBalance;
    private boolean status;
    private Double movementAmount;
    private Double availableBalance;
}
