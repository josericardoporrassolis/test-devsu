package com.devsu.data.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "person")
@Inheritance(strategy = InheritanceType.JOINED)
@Getter
@Setter
public class Person {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "person_id")
    private Integer id;

    private String name;
    private String gender;
    private Integer age;

    @Column(unique = true)
    private String identification;

    private String address;
    private String phone;

}
