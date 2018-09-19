package com.example.springdemo.controllers.v1;

import com.example.springdemo.api.v1.model.UserDTO;
import com.example.springdemo.api.v1.model.UserWithInfoDTO;
import com.example.springdemo.persistence.services.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(UserController.BASE_URL)
public class UserController {
    public static final String BASE_URL = "/api/v1/users";
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public UserWithInfoDTO createNewUser(@RequestBody UserWithInfoDTO userWithInfoDTO){
        return userService.createNewUser(userWithInfoDTO);
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public UserDTO getUserById(@PathVariable Long id) {
        return userService.getUserById(id);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<UserDTO> getAllUsers(@RequestParam Optional<Integer> offset, @RequestParam Optional<Integer> limit) {
        return userService.getAllUsers(offset, limit);
    }
}
