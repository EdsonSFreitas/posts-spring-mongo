package com.freitas.posts.service;

import com.freitas.posts.domain.Post;
import com.freitas.posts.dto.PostDTO;
import com.freitas.posts.repository.PostRepository;
import com.freitas.posts.service.exception.ObjNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author Edson da Silva Freitas
 * {@code @created} 23/08/2023
 * {@code @project} posts
 */
@Service
public class PostService {
    @Autowired
    private PostRepository repository;

    public PostDTO findById(String id) {
        return repository.findById(id).map(PostDTO::new).orElseThrow(() -> new ObjNotFoundException("Objeto não encontrado!"));
    }

    public Post fromDTO(PostDTO dto) {
        return new Post(dto.getId(), dto.getDate(), dto.getTitle(), dto.getBody(), dto.getAuthor());
    }

}