package com.sparta.todo.repository;

import com.sparta.todo.entity.Todo;
import org.springframework.data.jpa.repository.JpaRepository;


public interface TodoRespository extends JpaRepository<Todo, Long> {

}
