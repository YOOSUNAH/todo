package com.sparta.todo.config;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class UserAuth {

    @NotBlank(message = "token에서 userId를 찾을수없습니다.")
    private final Long userId;

}
