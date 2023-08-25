package com.freitas.posts.service;

import com.freitas.posts.domain.Post;
import com.freitas.posts.dto.AuthorDTO;
import com.freitas.posts.dto.CommentDTO;
import com.freitas.posts.dto.PostDTO;
import com.freitas.posts.repository.PostRepository;
import com.freitas.posts.resource.util.DecoderURLParam;
import com.freitas.posts.service.exception.ObjNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
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

    /**
     * Retrieves a page of PostDTO objects that match the given search criteria.
     *
     * @param text     the text to search in title, body or comments
     * @param minDate  the minimum date to filter the search results by
     * @param maxDate  the maximum date to filter the search results by
     * @param pageable the pagination information
     * @return a page of PostDTO objects that match the search criteria
     */
    public Page<PostDTO> fullSearch(String text, LocalDate minDate, LocalDate maxDate, Pageable pageable) {
        maxDate = maxDate.plusDays(1);
        Page<Post> searchResults = repository.fullSearch(DecoderURLParam.decodeParam(text), minDate, maxDate, pageable);
        return searchResults.map(PostDTO::new);
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