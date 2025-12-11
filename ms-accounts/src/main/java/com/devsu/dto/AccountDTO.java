package com.devsu.dto;

import lombok.Data;

@Data
public class AccountDTO {
    private String accountNumber;
    private String accountType;
    private Double initialBalance;
    private boolean status;
    private Long personId;
}