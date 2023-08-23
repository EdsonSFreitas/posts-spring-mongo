package com.freitas.posts.service;

import com.freitas.posts.domain.User;
import com.freitas.posts.dto.UserDTO;
import com.freitas.posts.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author Edson da Silva Freitas
 * {@code @created} 23/08/2023
 * {@code @project} posts
 */
@Service
public class UserService {
    @Autowired
    private UserRepository repository;

    public List<UserDTO> findAll(){
        List<User> users = repository.findAll();
        return users.stream().map(UserDTO::new).collect(java.util.stream.Collectors.toList());
    }

}