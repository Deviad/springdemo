package com.example.springdemo.persistence.services;
import com.example.springdemo.api.v1.model.UserDTO;
import com.example.springdemo.api.v1.model.UserWithInfoDTO;

import java.util.List;
import java.util.Optional;

public interface UserService {
    List<UserDTO> getAllUsers(Optional<Integer> offset, Optional<Integer> limit);
    UserDTO getUserById(Long id);
    UserDTO getUserByUsername(String username);
    UserWithInfoDTO createNewUser(UserWithInfoDTO userWithInfoDTO);
    UserDTO saveUserByDTO(Long id, UserDTO userDTO);
    UserDTO patchUser(Long id, UserDTO userDTO);
    void deleteCustomerById(Long id);
}
