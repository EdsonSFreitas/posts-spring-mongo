package com.freitas.posts.resource;

import com.freitas.posts.domain.Post;
import com.freitas.posts.domain.User;
import com.freitas.posts.dto.UserDTO;
import com.freitas.posts.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
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
@RequestMapping("/users")
public class UserResource {
    @Autowired
    private UserService service;

    @GetMapping
    public ResponseEntity<List<UserDTO>> findAll() {
        return ResponseEntity.ok().body(service.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserDTO> findById(@PathVariable String id) {
        User user = service.findById(id);
        UserDTO userDTO = new UserDTO(user.getId(), user.getName(), user.getEmail());
        return ResponseEntity.ok().body(userDTO);
    }

    @GetMapping("/{id}/posts")
    public ResponseEntity<List<Post>> findPostsById(@PathVariable String id) {
        User user = service.findById(id);
        return ResponseEntity.ok().body(user.getPosts());
    }

    @PostMapping()
    public ResponseEntity<Void> insert(@RequestBody UserDTO userDTO) {
        User user = service.fromDTO(userDTO);
        user = service.insert(user);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand(user.getId()).toUri();
        return ResponseEntity.created(uri).build();
    /* Desse modo retornará o objeto UserDTO com id, nome e email ao invés de retornar vazio,
    mas padrao é retornar apenas vazio e com location no header
		@PostMapping()
    public ResponseEntity<UserDTO> insert(@RequestBody UserDTO userDTO) {
        User user = service.fromDTO(userDTO);
        user = service.insert(user);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand(user.getId()).toUri();
        return ResponseEntity.created(uri).body(new UserDTO(user));
    } */
    }

    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity<Void> delete(@PathVariable String id) {
        User user = service.findById(id); //Lanca excecao se id nao existir
        service.delete(user.getId());
        return ResponseEntity.noContent().build();
        //RFC 7231 recomenda retornar corpo vazio na reposta com codigo 204
    }

    @PutMapping("/{id}")
    @Transactional
    public ResponseEntity<User> update(@RequestBody UserDTO userDTO, @PathVariable String id) {
        User user = service.fromDTO(userDTO);
        user.setId(id);
        user = service.update(user); // Retorna o objeto User atualizado
        URI location = ServletUriComponentsBuilder.fromCurrentRequest().build().toUri();
        return ResponseEntity.ok().location(location).body(user);
        //return ResponseEntity.ok().header("Content-Location", location.toString()).body(user);
    }

}