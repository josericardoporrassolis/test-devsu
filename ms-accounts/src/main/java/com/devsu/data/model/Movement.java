package com.devsu.data.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@Table(name = "movement")
public class Movement {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "movement_id")
    private Long movementId;

    @Column(name = "movement_date")
    private LocalDateTime date;

    @Column(name = "movement_type", length = 20)
    private String movementType;

    @Column(name = "movement_value")
    private Double value;

    private Double balance;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "account_number", nullable = false)
    private Account account;
}