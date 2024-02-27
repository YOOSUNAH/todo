package com.sparta.test;

import com.sparta.todo.entity.Todo;
import com.sparta.todo.entity.User;
import java.time.LocalDateTime;
import org.apache.commons.lang3.SerializationUtils;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.test.util.ReflectionTestUtils;

public class TodoTestUtils {

    public static Todo get(Todo todo, User user) {
        return get(todo, 1L, LocalDateTime.now(), user);
    }

    /**
     * 테스트용 할일 객체를 만들어주는 메서드
     * @param todo  : 복제할 할일 객체
     * @param id    : 설정할 아이디  (setter를 사용하면 안되는 것)
     * @param createDate : 설정할 생성일시 (setter를 사용하면 안되는 것)
     * @param user 연관관계 : 유저
     * @return 테스트용으로 생성된 할일 객체
     */
    public static Todo get(Todo todo, Long id, LocalDateTime createDate, User user) {

        Todo newTodo = SerializationUtils.clone(todo);  // clone 메서드는 괄호 안에 객체를 디카피해서 응답값으로 주는 것. (여기서 객체 (todo))
        // 이를 위해 Todo 가 Serializable을 implements해야 한다.
        // 그래야 SerializationUtils이 Serializable설정이 되어 있는 class에 대해서 copy를 할 수 있게 된다.
        // 그럼 새로운 new Todo 객체가 생성이 된다.

        ReflectionTestUtils.setField(newTodo, Todo.class, "id", id, Long.class);
        // ReflectionTestUtils 을 사용하면 setter 메서드가 없어도, 그 갑을 설정 할 수 있다.
        ReflectionTestUtils.setField(newTodo, Todo.class, "createDate", createDate, LocalDateTime.class);
        // 이렇게 초기화를 해주고,
        newTodo.setUser(user);
        // user  연관관계도 설정해준다.

        return newTodo; // 새로 만든 todo를 응답.
    }
}