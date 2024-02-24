package com.sparta.todo.dto.user;

import static org.assertj.core.api.Assertions.*;
import com.sparta.test.CommonTest;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import java.util.Set;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

class SignupRequestDtoTest implements CommonTest {


    @DisplayName("유저 요청 DTO 생성")
    @Nested
    class createUserRequestDTO {
        @DisplayName("유저 요청 DTO 생성 성공")
        @Test
        void createSignupRequestDto_success() {
        // given
        SignupRequestDto signupRequestDto = new SignupRequestDto();
        signupRequestDto.setUsername(TEST_USER_NAME);
        signupRequestDto.setPassword(TEST_USER_PASSWORD);

        // when
        Set<ConstraintViolation<SignupRequestDto>> violations = validate(signupRequestDto);  // validate 메서드 별도 생성

        // then
        assertThat(violations).isEmpty();
    }

        @Disabled // Github Action 기본 Runner 가 Java 21 버전으로 실행되어서 Disabled 처리함
        @DisplayName("유저 요청 DTO 생성 실패 - 잘못된 username")
        @Test
        void createUserRequestDTO_fail_wrongUserName() {
            // given
            SignupRequestDto signupRequestDto = new SignupRequestDto();
            signupRequestDto.setUsername("Invalid user name"); // Invalid username pattern , 4~10자 넘고, 대문자 있음 잘못된 이름 예시
            signupRequestDto.setPassword(TEST_USER_PASSWORD);     // Invalid password pattern

            // when
            Set<ConstraintViolation<SignupRequestDto>> violations = validate(signupRequestDto);

            // then
            assertThat(violations).hasSize(1);
            assertThat(violations)
                .extracting("message")
                .contains("\"^[a-z0-9]{4,10}$\"와 일치해야 합니다");
        }
        @Disabled // Github Action 기본 Runner 가 Java 21 버전으로 실행되어서 Disabled 처리함
        @DisplayName("유저 요청 DTO 생성 실패 - 잘못된 password")
        @Test
        void createUserRequestDTO_wrongPassword() {
            SignupRequestDto signupRequestDto = new SignupRequestDto();
            signupRequestDto.setUsername(TEST_USER_NAME); // Invalid username pattern
            signupRequestDto.setPassword("Invalid password");     // Invalid password pattern

            // when
            Set<ConstraintViolation<SignupRequestDto>> violations = validate(signupRequestDto);

            // then
            assertThat(violations).hasSize(1);
            assertThat(violations)
                .extracting("message")  // extracting 값을 뽑아온다.
                .contains("\"^[a-zA-Z0-9]{8,15}$\"와 일치해야 합니다"); // 위의 message가 contains() 이 메세지를 포함하고 있다.
        }
    }

// controller 에서 pattern을  @Valid 로 테스트 했음. @Interface valid -> 똑같은 방법 Validation을 사용하는 것
    private Set<ConstraintViolation<SignupRequestDto>> validate(SignupRequestDto userRequestDTO) {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        Validator validator = factory.getValidator();
        return validator.validate(userRequestDTO);  // pattern 만족하는지 체크 @Valid랑 동일한 역할
    }
}