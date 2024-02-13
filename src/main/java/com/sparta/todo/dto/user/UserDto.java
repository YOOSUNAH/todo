package com.sparta.todo.dto.user;

import com.sparta.todo.entity.User;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@EqualsAndHashCode
public class UserDto {

    private String username;

    public UserDto(User user){
        this.username = user.getUsername();
    }
}
