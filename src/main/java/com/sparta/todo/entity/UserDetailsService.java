package com.sparta.todo.entity;

import com.sparta.todo.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserDetailsService {


    private final UserRepository userRepository;
    public UserDetailsImpl getUserDetails(String username){
        User user = userRepository.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException("Not found" + username));
        return new UserDetailsImpl(user);
    }

}
