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

    @Mappings({
            @Mapping(source = "userInfo.name", target = "name"),
            @Mapping(source = "userInfo.surname", target = "surname"),
            @Mapping(source = "userInfo.telephone", target = "telephone")
    })
    UserDTO userToUserDTO(User user);

    @Mappings({
            @Mapping(target = "id", ignore = true),
            @Mapping(target = "userInfo.user", ignore = true),
            @Mapping(source = "name", target = "userInfo.name"),
            @Mapping(source = "surname", target = "userInfo.surname"),
            @Mapping(source = "telephone", target= "userInfo.telephone")
    })
    User userDTOtoUser(UserDTO userDTO);

}
