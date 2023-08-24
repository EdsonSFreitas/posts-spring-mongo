package com.freitas.posts.repository;

import com.freitas.posts.domain.Post;
import com.freitas.posts.domain.User;
import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * @author Edson da Silva Freitas
 * {@code @created} 23/08/2023
 * {@code @project} posts
 */
public interface PostRepository extends MongoRepository<Post, String> {
}