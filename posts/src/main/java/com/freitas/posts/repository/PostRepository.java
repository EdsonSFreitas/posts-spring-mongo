package com.freitas.posts.repository;

import com.freitas.posts.domain.Post;
import com.freitas.posts.dto.CommentDTO;
import com.freitas.posts.dto.PostDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.time.LocalDate;

/**
 * @author Edson da Silva Freitas
 * {@code @created} 23/08/2023
 * {@code @project} posts
 */
public interface PostRepository extends MongoRepository<Post, String> {
    Page<PostDTO> findByTitleContainingIgnoreCase(String text, Pageable pageable);

    Page<CommentDTO> findAllCommentsById(String id, Pageable pageable);

    /* Usando buscas personalizadas - https://docs.spring.io/spring-data/mongodb/docs/current/reference/html/#query-by-example.running */
    @Query("{ 'title': { $regex: ?0, $options: 'i' } }")
    Page<PostDTO> searchByTitle(String text, Pageable pageable);

    /**
     * Performs a full search (case-insensitive) on the database based on the given parameters.
     *
     * @param  text       the text to search for in the title, body or comments
     * @param  minDate    the minimum date for the search
     * @param  maxDate    the maximum date for the search
     * @param  pageable   the pagination information
     * @return            a page of PostDTO objects matching the search criteria
     */
    @Query("{ $and: [ " +
            "{ date: { $gte: ?1 } }, " +
            "{ date: { $lte: ?2 } }, " +
            "{ $or: [ " +
            "{ 'title': { $regex: ?0, $options: 'i' } }, " +
            "{ 'body': { $regex: ?0, $options: 'i' } }, " +
            "{ 'comments.text': { $regex: ?0, $options: 'i' } }" +
            "] }" +
            "] }")
    Page<Post> fullSearch(String text, LocalDate minDate, LocalDate maxDate, Pageable pageable);

}