package com.devsu.data.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "customer")
@PrimaryKeyJoinColumn(name = "person_id")
@Getter
@Setter
public class Customer extends Person {

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private Boolean status;

    @Column(unique = true, nullable = false)
    private String email;

}
