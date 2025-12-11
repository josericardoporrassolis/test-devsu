package com.devsu.exception;

import lombok.Getter;

@Getter
public class CustomerException extends RuntimeException {

    public static final String CUSTOMER_ALREADY = "El cliente existe";
    private final Integer existingId;

    public CustomerException(Integer existingId) {
        super(CUSTOMER_ALREADY);
        this.existingId = existingId;
    }

}
