package com.devsu.common;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MovementEvent {

    private String accountNumber;
    private String accountType;
    private String value;

}
