package com.freitas.posts.resource;

import com.freitas.posts.domain.Post;
import com.freitas.posts.dto.CommentDTO;
import com.freitas.posts.dto.PostDTO;
import com.freitas.posts.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

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

    @GetMapping("/{id}/comments")
    public ResponseEntity<List<CommentDTO>> findCommentsById(@PathVariable String id) {
        List<CommentDTO> allCommentsByPostId = service.findAllCommentsByPostId(id);
        return ResponseEntity.ok().body(allCommentsByPostId);
    }

    @PostMapping()
    public ResponseEntity<PostDTO> addPost(@RequestBody PostDTO dto) {
        Post post = service.addPost(dto.getTitle(), dto.getBody(), dto.getAuthor());
        PostDTO postDTo = new PostDTO(post.getId(), post.getDate(), post.getTitle(), post.getBody(), post.getAuthor());
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand(post.getId()).toUri();
        return ResponseEntity.created(uri).body(postDTo);
    }

    @PostMapping("/{id}/addcomment")
    public ResponseEntity<PostDTO> addComment(@PathVariable String id, @RequestBody CommentDTO dto) {
        Post post = service.addComment(id, dto.getText(), dto.getAuthor());
        PostDTO postDTo = new PostDTO(post.getId(), post.getDate(), post.getTitle(), post.getBody(), post.getAuthor());
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand(post.getId()).toUri();
        return ResponseEntity.created(uri).body(postDTo);
    }

}