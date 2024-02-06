package com.sparta.todo.service;

import com.sparta.todo.dto.*;
import com.sparta.todo.dto.user.LoginRequestDto;
import com.sparta.todo.dto.user.LoginResponseDto;
import com.sparta.todo.dto.user.SignupRequestDto;
import com.sparta.todo.entity.User;
import com.sparta.todo.jwt.JwtUtil;
import com.sparta.todo.repository.UserRepository;
import com.sparta.todo.statusEnum.StatusAndEnum;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    @Transactional
    public ApiResult signup(SignupRequestDto signupRequestDto) {
        String username = signupRequestDto.getUsername();
        String password = passwordEncoder.encode(signupRequestDto.getPassword());  // requestDto에서 가져온 Password 평문을 encode 암호화해서 password에 저장

        // 회원 중복 확인
        Optional<User> checkUsername = userRepository.findByUsername(username); // findByUsername메서드를 이용해서 username을 가져온다.
        // null check 하려고 Optional 로 받음.
        if (checkUsername.isPresent()) {  // Optional 내부에 isPresent 메서드 가 존재함 이를 이용.  값이 존재하는지 안하는지 확인해주는 메서드. 값이 있으면 true가 반환됨.
            // true 면, 값이 있다는 것이니, 중복된 사용자가 있다는 걸로 보고 throw 던짐.
           // throw new IllegalArgumentException("중복된 사용자가 존재합니다.");
            return new ApiResult(StatusAndEnum.DUPLICATED_USERNAME);

        }
        // 사용자 등록
        User user = new User(username, password);
        userRepository.save(user);  // 저장 됨.

        return new ApiResult(StatusAndEnum.SIGNUP_SUCCESS);
    }

    @Transactional
    public LoginResponseDto login(LoginRequestDto loginRequestDto, HttpServletResponse response) {
        String username = loginRequestDto.getUsername();
        String password = loginRequestDto.getPassword();

        // 사용자 확인
        User user = userRepository.findByUsername(username).orElseThrow(
            () -> new IllegalArgumentException("등록된 사용자가 없습니다.")
        );

        // 비밀번호 확인
        if (!passwordEncoder.matches(password, user.getPassword())) {
            throw new IllegalArgumentException("비밀번호가 일치하지 않습니다.");
        }

        // 로그인 성공 시, 토큰 생성
        String token = jwtUtil.createToken(user.getUserId());

        // header에 더하기
        jwtUtil.addHeader(response, token);

        ApiResult apiResult = new ApiResult(StatusAndEnum.LOGIN_SUCCESS);
        return new LoginResponseDto(user, token, apiResult);
    }
}


