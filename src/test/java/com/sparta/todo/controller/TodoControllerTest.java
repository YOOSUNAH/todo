package com.sparta.todo.controller;

import static org.mockito.BDDMockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.sparta.test.ControllerTest;
import com.sparta.test.TodoTest;
import com.sparta.test.TodoTestUtils;
import com.sparta.todo.dto.todo.TodoRequestDto;
import com.sparta.todo.dto.todo.TodoResponseDto;
import com.sparta.todo.dto.user.UserDto;
import com.sparta.todo.entity.User;
import com.sparta.todo.service.TodoService;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.concurrent.RejectedExecutionException;

import org.hamcrest.Matchers;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;

@WebMvcTest(TodoController.class)
class TodoControllerTest extends ControllerTest implements TodoTest {

    @MockBean
    private TodoService todoService;

    @DisplayName("할일 생성 요청")
    @Test
    void postTodo() throws Exception {
        // given

        // when
        var action = mockMvc.perform(post("/api/todos")  // perform메서드는 mockMvc에서 실제로 웹호출이 발생하는 것처럼 해줌
            .contentType(MediaType.APPLICATION_JSON)
            .accept(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(TEST_TODO_REQUEST_DTO))); // content :MockMvc에서 http요청body룰 솔종허눈 매서드, Json형식의데이터를 담아서, objcetMapper를 이용해서  java객체를 json으로 변환함.

        // then
        action.andExpect(status().isCreated());

        verify(todoService, times(1)).saveTodo(any(TodoRequestDto.class), eq(TEST_USER));
        //verify : Mockito에서 제공. 실제 어떤 메서드가 호출이 되었는지 체크하는 용도,
        // times 몇회 호출되었나.
        // .메서드이름(paraneter),  any(TodoRequestDto.class) : TodoRequestDto.class 로 된 객체아무거나 괜찮다., eq(TEST_USER) : (equals) TEST_USER 이 값과 같아야 한다.
    }


    @Nested
    @DisplayName("할일 조회 요청")
    class getTodo {
        @DisplayName("할일 조회 요청 성공")
        @Test
        void getTodo_success() throws Exception {
            // given
            given(todoService.getTodoById(eq(TEST_TODO_ID))).willReturn(TEST_TODO_RESPONSE_DTO);

            // when
            var action = mockMvc.perform(get("/api/todos/{todoId}", TEST_TODO_ID)  // {todoId}로 된 값을 그 뒤에 , 하고 TEST_TODO_ID 로 넣어줌.
                .accept(MediaType.APPLICATION_JSON));


            // then
            action
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").value(TEST_TODO_TITLE)) // jsonPath : resultAction andExpect에서 사용하는 것. resultMatcher class를 생성해주는 생성자.
                .andExpect(jsonPath("$.content").value(TEST_TODO_CONTENT));
            //JsonPathResultMatchers(expression).value(matcher);
            // espression 표현자 형태 : ex)  "$.content"  (응답이 json형태).  json 안에 있는 content 값을 비교하겠다는 뜻. (뒤에 value() 괄호안에 있는 것과 비교하겠다.)
            // $ : 응답의 root 경로를 뜻함. 배열이면 $[0] 0번째라고 표시를 해줘야 함.
        }// .value(TEST_TODO_CONTENT) 어떤 값이랑 같은지를 .value() 하고 괄호 안에 넣어준다.

        @DisplayName("할일 조회 요청 실패 - 존재하지 않는 할일ID")
        @Test
        void getTodo_fail_todoIdNotExist() throws Exception {
            // given 으로 willThrow 설정해주기.
            given(todoService.getTodoById(eq(TEST_TODO_ID))).willThrow(new IllegalArgumentException()); //willThrow를 통해 예외를 던지도록 Mock에다가 설정할 수 있음.

            // when
            var action = mockMvc.perform(get("/api/todos/{todoId}", TEST_TODO_ID)
                .accept(MediaType.APPLICATION_JSON));

            // then
            action
                .andExpect(status().isBadRequest());
        }
    }

    @DisplayName("할일 목록 조회 요청")
    @Test
    void getTodoList() throws Exception {
        // given
        var testTodo1 = TodoTestUtils.get(TEST_TODO, 1L, LocalDateTime.now(), TEST_USER);
        var testTodo2 = TodoTestUtils.get(TEST_TODO, 2L, LocalDateTime.now().minusMinutes(1), TEST_USER);
        var testAnotherTodo = TodoTestUtils.get(TEST_TODO, 3L, LocalDateTime.now(), TEST_ANOTHER_USER);

        given(todoService.getUserTodoMap()).willReturn(  // todoService의 getUserTodoMap 메서드를 호출했을 때 willReturn() 괄호 속 값을 응답 받아야 한다.
            Map.of(new UserDto(TEST_USER), List.of(new TodoResponseDto(testTodo1), new TodoResponseDto(testTodo2)),
                new UserDto(TEST_ANOTHER_USER), List.of(new TodoResponseDto(testAnotherTodo))));
            //   Map<UserDto, List<TodoResponseDto>> responseDTOMap = todoService.getUserTodoMap(); Map 형태
           // Map.of 로 Map을 만든다. (key, value), (key, value) 형태

        // when
        // perform으로 호출해보기
        var action = mockMvc.perform(get("/api/todos")  // 응답값이 나옴 -> action  // get메서드로 "/api/todos"를 호출
            .accept(MediaType.APPLICATION_JSON));

        // then
        action
            .andExpect(status().isOk())
            .andExpect(jsonPath("$[?(@.user.username=='" + TEST_USER.getUsername() + "')].todoList[*].id")
                // $ : 응답의 root, [] 배열 값 : $[]
                // @ : 실제 해당 배열 값들의 root
                // todoList[*] *이라는 건 todolist의 모든 걸 조회하겠다. .id id를 다 가져오겠다.

                .value(Matchers.containsInAnyOrder(testTodo1.getTodoId().intValue(), testTodo2.getTodoId().intValue())))
            //  .value 로 값을 비교
            //  Matchers.containsInAnyOrder 순서 상관 없이 다 포함되어있는지 확인

            .andExpect(jsonPath("$[?(@.user.username=='" + TEST_ANOTHER_USER.getUsername() + "')].todoList[*].id")
                .value(Matchers.containsInAnyOrder(testAnotherTodo.getTodoId().intValue())));
        verify(todoService, times(1)).getUserTodoMap();
        // todoService에서 getUserTodoMap() 메서드 호출하는지 확인
    }

    @Nested
    @DisplayName("할일 수정 요청")
    class putTodo {
        @DisplayName("할일 수정 요청 성공")
        @Test
        void putTodo_success() throws Exception {
            // given
            given(todoService.updateTodo(eq(TEST_TODO_ID), eq(TEST_TODO_REQUEST_DTO), any(User.class))).willReturn(TEST_TODO_RESPONSE_DTO);

            // when
            var action = mockMvc.perform(put("/api/todos/{todoId}", TEST_TODO_ID)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(TEST_TODO_REQUEST_DTO)));

            // then
            action
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").value(TEST_TODO_TITLE))
                .andExpect(jsonPath("$.content").value(TEST_TODO_CONTENT));
        }

        @DisplayName("할일 수정 요청 실패 - 권한 없음")
        @Test
        void putTodo_fail_rejected() throws Exception {
            // given
            given(todoService.updateTodo(eq(TEST_TODO_ID), eq(TEST_TODO_REQUEST_DTO), any(User.class))).willThrow(new RejectedExecutionException());
            // eq : Mockito 라이브러리에서 제공하는 메서드, 매개변수가 동일한지를 검증 (equals의 약자)
            // willReturn : Mockito에서 사용되는 메서드, Mock객체에 대한 메서드 호출이 예상된 경우, 해당 메서드가 어떤 값을 반환할지 지정하는데 사용.

            // updateTodo 메서드가 전달값 TEST_TODO_ID, TEST_TODO_REQUEST_DTO, 그리고 어떠한 User 객체와 함께 호출되었을 때,
            // TEST_TODO_RESPONSE_DTO를 반환하도록 설정

            // when
            var action = mockMvc.perform(put("/api/todos/{todoId}", TEST_TODO_ID)
                .contentType(MediaType.APPLICATION_JSON)
                // contentType : HTTP 요청이나 응답의 컨텐츠 유형을 나타내는 헤더
                // -> 여기서는 해당 HTTP 요청의 본문(content)이 JSON 형식임을 나타낸다.

                .accept(MediaType.APPLICATION_JSON)
                //  HTTP 요청 헤더 중 하나로, 클라이언트가 서버로부터 응답 받을 때 원하는 미디어 타입
                // ->  클라이언트가 서버로부터의 응답을 JSON 형식으로 받고자 한다는 걸 의미

                .content(objectMapper.writeValueAsString(TEST_TODO_REQUEST_DTO)));
                // content : HTTP 요청의 본문(body)을 설정하는 부분
               // -> objectMapper를 사용하여, 객체를 JSON 문자열로 변환하고, 이를 HTTP 요청의 본문으로 설정

            // then
            action
                .andExpect(status().isBadRequest());
        }

        @DisplayName("할일 수정 요청 실패 - 존재하지 않는 할일ID")
        @Test
        void putTodo_fail_illegalArgument() throws Exception {
            // given
            given(todoService.updateTodo(eq(TEST_TODO_ID), eq(TEST_TODO_REQUEST_DTO), any(User.class))).willThrow(new IllegalArgumentException());
            //willThrow(new IllegalArgumentException()
            // IllegalArgumentException을 던질 것이다.

            // when
            var action = mockMvc.perform(put("/api/todos/{todoId}", TEST_TODO_ID)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(TEST_TODO_REQUEST_DTO)));

            // then
            action
                .andExpect(status().isBadRequest());
        }
    }

    @Nested
    @DisplayName("할일 완료 요청")
    class completeTodo {
        @DisplayName("할일 완료 요청 성공")
        @Test
        void completeTodo_success() throws Exception {
            // given
            TEST_TODO_RESPONSE_DTO.setIsCompleted(true);
            given(todoService.completeTodo(eq(TEST_TODO_ID), any(User.class))).willReturn(TEST_TODO_RESPONSE_DTO);

            // when
            var action = mockMvc.perform(patch("/api/todos/{todoId}/complete", TEST_TODO_ID)
                .accept(MediaType.APPLICATION_JSON));

            // then
            action
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").value(TEST_TODO_TITLE))
                .andExpect(jsonPath("$.content").value(TEST_TODO_CONTENT))
                .andExpect(jsonPath("$.isCompleted").value(true));
        }

        @DisplayName("할일 완료 요청 실패 - 권한 없음")
        @Test
        void completeTodo_fail_rejected() throws Exception {
            // given
            given(todoService.completeTodo(eq(TEST_TODO_ID), any(User.class))).willThrow(new RejectedExecutionException());

            // when
            var action = mockMvc.perform(patch("/api/todos/{todoId}/complete", TEST_TODO_ID)
                .accept(MediaType.APPLICATION_JSON));

            // then
            action
                .andExpect(status().isBadRequest());
        }

        @DisplayName("할일 완료 요청 실패 - 존재하지 않는 할일ID")
        @Test
        void completeTodo_fail_illegalArgument() throws Exception {
            // given
            given(todoService.completeTodo(eq(TEST_TODO_ID), any(User.class))).willThrow(new IllegalArgumentException());

            // when
            var action = mockMvc.perform(patch("/api/todos/{todoId}/complete", TEST_TODO_ID)
                .accept(MediaType.APPLICATION_JSON));

            // then
            action
                .andExpect(status().isBadRequest());
        }
    }


}