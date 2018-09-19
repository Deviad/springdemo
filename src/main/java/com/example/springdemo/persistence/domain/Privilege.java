package com.example.springdemo.persistence.domain;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity(name="Privilege")
@Table(name="privileges")
public class Privilege {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    @Column(nullable = false, unique = true)
    private String name;


}
