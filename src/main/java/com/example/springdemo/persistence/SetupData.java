package com.example.springdemo.persistence;

import com.example.springdemo.persistence.domain.Privilege;
import com.example.springdemo.persistence.domain.User;
import com.example.springdemo.persistence.domain.UserInfo;
import com.example.springdemo.persistence.repositories.PrivilegeRepository;
import com.example.springdemo.persistence.repositories.UserInfoRepository;
import com.example.springdemo.persistence.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.*;

@Component
public class SetupData {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PrivilegeRepository privilegeRepository;

    @Autowired
    private UserInfoRepository userInfoRepository;

    @Autowired
    private PasswordEncoder encoder;

    @PostConstruct
    public void init() {
//        initPrivileges();
//        initUsers();
    }

    private void initUsers() {
        Privilege privilege1 = privilegeRepository.findByName("FOO_READ_PRIVILEGE");
        Privilege privilege2 = privilegeRepository.findByName("FOO_WRITE_PRIVILEGE");
        User user1 = new User();
        user1.setUsername("john");
        user1.setPassword(encoder.encode("123"));
        user1.setPrivileges(new HashSet<>(Arrays.asList(privilege1)));
        userRepository.save(user1);
        UserInfo userInfo1 = new UserInfo();
        userInfo1.setName("Davide");
        userInfo1.setTelephone("00000");
        userInfoRepository.save(userInfo1);
        user1.setUserInfo(userInfo1);
        userRepository.save(user1);


//        User user2 = new User();
//        user2.setUsername("tom");
//        user2.setPassword(encoder.encode("111"));
//        user2.setPrivileges(new HashSet<>(Arrays.asList(privilege1, privilege2)));
//        User savedUser2 = userRepository.save(user2);
//        savedUser2.getUserInfo().setUser(savedUser2);
//        savedUser2.getUserInfo().setName("johnName");
//        userRepository.save(savedUser2);
    }
    private void initPrivileges() {
        Privilege privilege1 = new Privilege();
        privilege1.setName("FOO_READ_PRIVILEGE");
        privilegeRepository.save(privilege1);
        Privilege privilege2 = new Privilege();
        privilege2.setName("FOO_WRITE_PRIVILEGE");
        privilegeRepository.save(privilege2);
    }
}
