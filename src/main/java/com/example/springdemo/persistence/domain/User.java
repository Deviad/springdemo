package com.example.springdemo.persistence.domain;

import lombok.Data;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Data
@Entity(name = "User")
@Table(name="users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    private String username;
    private String password;

    @OneToOne
    private UserInfo userInfo;
    @ManyToMany(
            fetch = FetchType.EAGER,
            cascade = {
                CascadeType.MERGE
            }
    )
    @JoinTable(
            name = "users_privileges",
            joinColumns = @JoinColumn(name = "user_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "privilege_id", referencedColumnName = "id")
    )
    private Set<Privilege> privileges = new HashSet<>();
}
