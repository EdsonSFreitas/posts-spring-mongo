package com.freitas.posts.resource;

import com.freitas.posts.domain.User;
import com.freitas.posts.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author Edson da Silva Freitas
 * {@code @created} 23/08/2023
 * {@code @project} posts
 */
@RestController
@RequestMapping("/users")
public class UserResource {
    @Autowired
    private UserService service;

    @GetMapping
    public ResponseEntity<List<User>> findAll() {
        /*User maria = new User("1", "Maria", "maria@egmail.com");
        User alex = new User("2", "Alex", "alex@egmail.com");
        List<User> list = new ArrayList<>();
        list.addAll(Arrays.asList(maria, alex));*/
        return ResponseEntity.ok().body(service.findAll());
    }
}