package com.freitas.posts.repository;

import com.freitas.posts.domain.Post;
import com.freitas.posts.domain.User;
import com.freitas.posts.dto.CommentDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

/**
 * @author Edson da Silva Freitas
 * {@code @created} 23/08/2023
 * {@code @project} posts
 */
public interface PostRepository extends MongoRepository<Post, String> {
    Page<Post> findByTitleContainingIgnoreCase(String text, Pageable pageable);
    Page<Post> findAllBy(Pageable pageable);
    Page<CommentDTO> findAllCommentsById (String id, Pageable pageable);
}