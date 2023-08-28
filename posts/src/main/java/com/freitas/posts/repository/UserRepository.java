package com.freitas.posts.repository;

import com.freitas.posts.domain.Post;
import com.freitas.posts.domain.User;
import com.freitas.posts.dto.UserDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

/**
 * @author Edson da Silva Freitas
 * {@code @created} 23/08/2023
 * {@code @project} posts
 */
@Repository
public interface UserRepository extends MongoRepository<User, String> {
    Page<User> findAll(Pageable pageable);
}