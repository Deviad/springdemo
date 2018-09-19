package com.example.springdemo.persistence.services;

import com.example.springdemo.api.v1.mapper.UserMapper;
import com.example.springdemo.api.v1.model.UserDTO;
import com.example.springdemo.api.v1.model.UserWithInfoDTO;
import com.example.springdemo.controllers.v1.UserController;
import com.example.springdemo.persistence.domain.User;
import com.example.springdemo.persistence.domain.UserInfo;
import com.example.springdemo.persistence.repositories.UserInfoRepository;
import com.example.springdemo.persistence.repositories.UserRepository;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {

    private final UserMapper userMapper;
    private final UserRepository userRepository;
    private final UserInfoRepository userInfoRepository;

    public UserServiceImpl(@SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection") UserMapper userMapper, UserInfoRepository userInfoRepository, UserRepository userRepository) {
        this.userMapper = userMapper;
        this.userRepository = userRepository;
        this.userInfoRepository = userInfoRepository;
    }

    @Override
    public List<UserDTO> getAllUsers(Optional<Integer> offset, Optional<Integer> limit) {
        /*
            We either want to use pagination feature or not using it at all.
         */


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
    public UserDTO getUserByUsername(String username) {
        return Optional.of(userRepository.findUserByUsername(username))
            .map(user -> {
                Long id = user.getId();
                UserDTO userDTO = userMapper.userToUserDTO(user);
                userDTO.setUserUrl(getUserUrl(id));
                return userDTO;
            })
            .orElseThrow(ResourceNotFoundException::new);
    }

    @Override
    public UserWithInfoDTO createNewUser(UserWithInfoDTO userWithInfoDTO) {

        return saveAndReturnDTO(userWithInfoDTO);
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

    /*
        When using lazy loading for entity relationships, the value of the join attribute must be set manually and
        the join attribute must be flagged as nullable.
        In order to set the join attribute value, we need to set the join attribute user_id to the value
        of the user entity id.
        After that we need to save the user entity once more to make the data persistent.
     */
    private UserWithInfoDTO saveAndReturnDTO(UserWithInfoDTO userWithInfoDTO) {
//        UserInfo userInfo = new UserInfo();
        UserInfo filledInfo = userMapper.userWithInfoDTOtoUserinfo(userWithInfoDTO);
        UserInfo savedUserInfo = userInfoRepository.save(filledInfo);

        User user = userMapper.userWithInfoDTOtoUser(userWithInfoDTO);
        user.setUserInfo(savedUserInfo);

        User savedUser = userRepository.save(user);

        UserWithInfoDTO returnDto = userMapper.userToUserWithinfoDTO(savedUser);
        returnDto.setUserUrl(getUserUrl(savedUser.getId()));
        return returnDto;
    }

    private String getUserUrl(Long id) {
        return UserController.BASE_URL + "/" + id;
    }
}
