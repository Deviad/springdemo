package com.example.springdemo.persistence.domain;

import lombok.Data;

import javax.persistence.*;
import java.util.Collection;
import java.util.HashSet;

@Data
@Entity(name="Privilege")
@Table(name="privileges")
public class Privilege {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Column(unique = true)
    private String name;

    @ManyToMany(mappedBy = "privileges", fetch = FetchType.LAZY, cascade = {CascadeType.MERGE})
    private Collection<Role> roles = new HashSet<>();
}

