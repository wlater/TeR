package com.mps.blindsec.dto.mapper;

import com.mps.blindsec.dto.UserDTO;
import com.mps.blindsec.model.User;

public class UserMapper {
    //Faz as conversões de User para UserDTO
    public static UserDTO toUserDto(User user) {
        return new UserDTO()
                .setId(user.getId())
                .setName(user.getName())
                .setEmail(user.getEmail());
    }

    //Faz as conversões de UserDTO para User
    public static User toUser(UserDTO userDto){
        return new User()
        .setEmail(userDto.getEmail())
        .setName(userDto.getName())
        .setId(userDto.getId())
        .setPasswordHash(userDto.getPassword());
    }
}
