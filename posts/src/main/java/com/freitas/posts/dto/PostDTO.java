package com.freitas.posts.dto;

import com.freitas.posts.domain.Post;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Edson da Silva Freitas
 * {@code @created} 24/08/2023
 * {@code @project} posts
 */
public class PostDTO implements Serializable {
    private static final long serialVersionUID = 1L;
    private String id;
    private LocalDateTime date;
    private String title;
    private String body;
    private AuthorDTO author;
    private List<CommentDTO> comments;

    public PostDTO() {

    }

    public PostDTO(String id, LocalDateTime date, String title, String body, AuthorDTO author) {
        this.id = id;
        this.date = date;
        this.title = title;
        this.body = body;
        this.author = author;
    }

    public PostDTO(Post post) {
        this.id = post.getId();
        this.date = post.getDate();
        this.title = post.getTitle();
        this.body = post.getBody();
        this.author = post.getAuthor();
        this.comments = post.getComments().stream().map(CommentDTO::new).collect(Collectors.toList());
    }

    public PostDTO(PostDTO postDTO) {

    }

    public String getId() {
        return id;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public String getTitle() {
        return title;
    }

    public String getBody() {
        return body;
    }

    public AuthorDTO getAuthor() {
        return author;
    }

    public List<CommentDTO> getComments() {
        return comments;
    }
}