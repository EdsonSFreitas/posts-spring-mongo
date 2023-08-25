package com.freitas.posts.service;

import com.freitas.posts.domain.Post;
import com.freitas.posts.dto.AuthorDTO;
import com.freitas.posts.dto.CommentDTO;
import com.freitas.posts.dto.PostDTO;
import com.freitas.posts.repository.PostRepository;
import com.freitas.posts.service.exception.ObjNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Edson da Silva Freitas
 * {@code @created} 23/08/2023
 * {@code @project} posts
 */
@Service
public class PostService {
    @Autowired
    private PostRepository repository;

    public static Post fromDTO(PostDTO dto) {
        return new Post(dto.getId(), dto.getDate(), dto.getTitle(), dto.getBody(), dto.getAuthor());
    }

    public PostDTO findById(String id) {
        return repository.findById(id).map(PostDTO::new).orElseThrow(() -> new ObjNotFoundException("Objeto não encontrado!"));
    }

    public Page<PostDTO> findAll(Pageable pageable) {
        return repository.findAll(pageable).map(PostDTO::new);
    }

    public Page<CommentDTO> findAllCommentsByPostId(String id, Pageable pageable) {
        Post post = repository.findById(id).orElseThrow(() -> new ObjNotFoundException("Objeto não encontrado!"));
        return new PageImpl<>(post.getComments(), pageable, post.getComments().size());
    }

    /* Usando busca por titulo com Query Method */
    public Page<PostDTO> findByTitleContainingIgnoreCase(String text, Pageable pageable) {
        return repository.findByTitleContainingIgnoreCase(text, pageable);
    }

    /* Usando busca por titulo com @Query */
    public Page<PostDTO> searchByTitle(String text, Pageable pageable) {
        return repository.searchByTitle(text, pageable);
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