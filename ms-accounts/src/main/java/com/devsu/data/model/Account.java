package com.devsu.data.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "account")
public class Account {

    @Id
    @Column(name = "account_number", length = 20)
    private String accountNumber;

    @Column(name = "account_type", length = 20)
    private String accountType;

    @Column(name = "initial_balance")
    private Double initialBalance;

    private boolean status;

    @Column(name = "person_id", nullable = false)
    private Long personId;

    @OneToMany(mappedBy = "account", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @ToString.Exclude
    private List<Movement> movements;
}
