package com.example.springdemo.domain;


import lombok.Data;

import javax.persistence.*;

@Data
@Entity
public class UserInfo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @OneToOne
    @JoinColumn(name = "user_id")
    private User user;

    private String name;

    private String surname;

    private String telephone;


}
