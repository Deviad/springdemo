package com.example.springdemo.api.v1.mapper;

import com.example.springdemo.domain.User;
import com.example.springdemo.api.v1.model.UserDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface UserMapper {
    UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);


    UserDTO userToUserDTO(User user);
    @Mappings(
            @Mapping(target = "id", ignore = true)
    )
    User userDTOtoUser(UserDTO userDTO);

}
