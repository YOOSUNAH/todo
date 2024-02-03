package com.sparta.todo.controller;

import com.sparta.todo.dto.LoginRequestDto;
import com.sparta.todo.dto.SignupRequestDto;
import com.sparta.todo.dto.UserResponseDto;
import com.sparta.todo.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/user")
public class UserController {

    private final UserService userService;

    @PostMapping("/signup")
    @Operation(
        summary = "회원 가입",
        description = "이름과 비밀번호를 입력해 주세요",
        responses = {
            @ApiResponse(responseCode = "200", description = "회원 가입 성공"),
            @ApiResponse(responseCode = "500", description = "회원 가입 에러")
        }
    )
    public UserResponseDto signup(@RequestBody @Valid SignupRequestDto signupRequestDto, HttpServletResponse res){
        UserResponseDto dto = userService.signup(signupRequestDto);
        res.addHeader("Authorization", dto.getToken());
        return dto;
    }

    @PostMapping("/login")
    @Operation(
        summary = "로그인",
        description = "이름과 비밀번호를 입력해 주세요",
        responses = {
            @ApiResponse(responseCode = "200", description = "로그인 성공"),
            @ApiResponse(responseCode = "500", description = "로그인 에러")
        }
    )
    public UserResponseDto login(@RequestBody LoginRequestDto loginRequestDto, HttpServletResponse res){
        UserResponseDto dto =  userService.login(loginRequestDto);
        res.addHeader("Authorization", dto.getToken());
        return dto;
    }


}
