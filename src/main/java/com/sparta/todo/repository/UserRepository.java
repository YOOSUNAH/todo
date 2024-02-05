package com.sparta.todo.repository;

import com.sparta.todo.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUsername(String username);

    @Query("SELECT u FROM User u WHERE u.userId IN :ids")
    List<User> findByIds(@Param("ids") List<Long> userIdList);


    User findByName(String username);


}
