package com.freitas.posts.resource;

import com.freitas.posts.domain.Post;
import com.freitas.posts.dto.PostDTO;
import com.freitas.posts.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Edson da Silva Freitas
 * {@code @created} 23/08/2023
 * {@code @project} posts
 */
@RestController
@RequestMapping("/posts")
public class PostResource {
    @Autowired
    private PostService service;

    @GetMapping("/{id}")
    public ResponseEntity<PostDTO> findById(@PathVariable String id) {
        return ResponseEntity.ok().body(service.findById(id));
    }
}