package com.example.springdemo.services;

import com.example.springdemo.api.v1.mapper.UserMapper;
import com.example.springdemo.api.v1.model.UserDTO;
import com.example.springdemo.controllers.v1.UserController;
import com.example.springdemo.domain.User;
import com.example.springdemo.repositories.UserRepository;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {

    private final UserMapper userMapper;
    private final UserRepository userRepository;

    public UserServiceImpl(UserMapper userMapper, UserRepository userRepository) {
        this.userMapper = userMapper;
        this.userRepository = userRepository;
    }

    @Override
    public List<UserDTO> getAllUsers(Optional<Integer> offset, Optional<Integer> limit) {
        if (!offset.isPresent() && !limit.isPresent()) {
            return userRepository.findAll()
                    .stream()
                    .map(user -> {
                        UserDTO userDTO = userMapper.userToUserDTO(user);
                        userDTO.setUserUrl(getUserUrl(user.getId()));
                        return userDTO;
                    })
                    .collect(Collectors.toList());
        }
        return userRepository.findAllUsersWithLimit(PageRequest.of(offset.orElse(0), limit.orElse(10)))
                .stream()
                .map(user -> {
                    UserDTO userDTO = userMapper.userToUserDTO(user);
                    userDTO.setUserUrl(getUserUrl(user.getId()));
                    return userDTO;
                })
                .collect(Collectors.toList());
    }

    @Override
    public UserDTO getUserById(Long id) {
        return userRepository.findById(id)
                .map(userMapper::userToUserDTO)
                .map(userDTO -> {
                    userDTO.setUserUrl(getUserUrl(id));
                    return userDTO;
                }).orElseThrow(ResourceNotFoundException::new);
    }

    @Override
    public UserDTO createNewUser(UserDTO userDTO) {
        return saveAndReturnDTO(userMapper.userDTOtoUser(userDTO));
    }

    @Override
    public UserDTO saveUserByDTO(Long id, UserDTO userDTO) {
        return null;
    }

    @Override
    public UserDTO patchUser(Long id, UserDTO userDTO) {
        return null;
    }

    @Override
    public void deleteCustomerById(Long id) {

    }

    private UserDTO saveAndReturnDTO(User user) {
        User savedUser = userRepository.save(user);

        UserDTO returnDto = userMapper.userToUserDTO(savedUser);

        returnDto.setUserUrl(getUserUrl(savedUser.getId()));

        return returnDto;
    }

    private String getUserUrl(Long id) {
        return UserController.BASE_URL + "/" + id;
    }
}
