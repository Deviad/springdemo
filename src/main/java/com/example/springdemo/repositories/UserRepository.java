package com.example.springdemo.repositories;

import com.example.springdemo.domain.User;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface UserRepository extends JpaRepository<User, Long> {
    @Query("SELECT u FROM User u")
    List<User> findAllUsersWithLimit(Pageable pageable);

//    default List<User>findAllWithLimit10() {
//        return findAllUsersWithLimit(new PageRequest(0, 10));
//    }
}
