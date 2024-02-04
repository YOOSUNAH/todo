package com.sparta.todo.dto.user;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SignupRequestDto {

    @NotBlank(message = "이름을 입력해주세요. (4글자 이상 10글자 이하로)")
    @Size(min=4, max=10)
    @Pattern(regexp = "^[a-z0-9]+$")
    private String username;

    @NotBlank(message = "비밀번호를 입력해주세요. (8글자 이상 15글자 이하로)")
    @Size(min=8, max=15)
    @Pattern(regexp = "^[a-zA-Z0-9]+$")
    private String password;

}
