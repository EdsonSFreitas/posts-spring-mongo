package com.freitas.posts.service;

import com.freitas.posts.domain.Post;
import com.freitas.posts.domain.User;
import com.freitas.posts.dto.AuthorDTO;
import com.freitas.posts.dto.CommentDTO;
import com.freitas.posts.dto.PostDTO;
import com.freitas.posts.repository.PostRepository;
import com.freitas.posts.service.exception.ObjNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;

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

    public static Post fromDTO(PostDTO dto) {
        return new Post(dto.getId(), dto.getDate(), dto.getTitle(), dto.getBody(), dto.getAuthor());
    }

    public Post addPost(String title, String body, AuthorDTO author) {
        Post post = new Post(null, LocalDateTime.now(), title, body, author);
        return repository.save(post);
    }

    public Post addComment(String id, String comment, AuthorDTO author) {
        CommentDTO com = new CommentDTO(comment, LocalDateTime.now(), author);
        Post post = repository.findById(id).orElseThrow(() -> new ObjNotFoundException("Objeto não encontrado!"));
        post.addComment(com);
        return repository.save(post);
    }


}