package com.example.springdemo.persistence;

import com.example.springdemo.persistence.domain.Privilege;
import com.example.springdemo.persistence.domain.Role;
import com.example.springdemo.persistence.domain.User;
import com.example.springdemo.persistence.domain.UserInfo;
import com.example.springdemo.persistence.repositories.PrivilegeRepository;
import com.example.springdemo.persistence.repositories.RoleRepository;
import com.example.springdemo.persistence.repositories.UserInfoRepository;
import com.example.springdemo.persistence.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import javax.persistence.EntityExistsException;
import javax.persistence.EntityNotFoundException;
import java.util.*;

@Component
public class SetupData {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PrivilegeRepository privilegeRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private UserInfoRepository userInfoRepository;

    @Autowired
    private PasswordEncoder encoder;

    @PostConstruct
    public void init() {
        initPrivileges();
        initRoles();
        initUsers();
    }

    public void initUsers() {
        if(userRepository.findUserByUsername("pippo") == null) {
            Role adminRole = roleRepository.findAll().stream().filter(role-> role.getName().equals("ROLE_ADMIN")).findFirst().orElseThrow(EntityNotFoundException::new);
            User user1 = new User();
            user1.setUsername("pippo");
            user1.setPassword(encoder.encode("123"));
            user1.setRoles(new LinkedHashSet<>(Arrays.asList(adminRole)));
            userRepository.save(user1);
            UserInfo userInfo1 = new UserInfo();
            userInfo1.setName("Davide");
            userInfo1.setTelephone("00000");
            userInfoRepository.save(userInfo1);
            user1.setUserInfo(userInfo1);
            userRepository.save(user1);
        }

//        User user2 = new User();
//        user2.setUsername("tom");
//        user2.setPassword(encoder.encode("111"));
//        user2.setPrivileges(new HashSet<>(Arrays.asList(privilege1, privilege2)));
//        User savedUser2 = userRepository.save(user2);
//        savedUser2.getUserInfo().setUser(savedUser2);
//        savedUser2.getUserInfo().setName("johnName");
//        userRepository.save(savedUser2);
    }
    public void initPrivileges() {
        createPrivilegeIfNotFound("READ_PRIVILEGE");
        createPrivilegeIfNotFound("WRITE_PRIVILEGE");
    }
    public void initRoles() {
        Privilege readPrivilege
                = createPrivilegeIfNotFound("READ_PRIVILEGE");
        Privilege writePrivilege
                = createPrivilegeIfNotFound("WRITE_PRIVILEGE");
        Set<Privilege> adminPrivileges = new LinkedHashSet<>(Arrays.asList(readPrivilege, writePrivilege));
        createRoleIfNotFound("ROLE_ADMIN", adminPrivileges);
    }
    public Privilege createPrivilegeIfNotFound(String name) {

        Privilege privilege = privilegeRepository.findByName(name);
        if (privilege == null) {
            privilege = new Privilege();
            privilege.setName(name);
            privilegeRepository.save(privilege);
        }
        return privilege;
    }

//    @Transactional
//    public User createUserIfNotFound(String username, String password, String telephone) {
//
//        User user = userRepository.findUserByUsername(username);
//        if (user == null) {
//            user = new User();
//            user.setUsername(username);
//            userRepository.save(user);
//        }
//        return user;
//    }
    public Role createRoleIfNotFound(String name, Set<Privilege> privileges) {

        Role role = roleRepository.findByName(name);
        if (role == null) {
            role = new Role();
            role.setName(name);
            role.setPrivileges(privileges);
            roleRepository.save(role);
        }
        return role;
    }
}
