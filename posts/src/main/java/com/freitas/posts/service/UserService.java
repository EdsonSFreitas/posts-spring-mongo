package com.freitas.posts.service;

import com.freitas.posts.domain.User;
import com.freitas.posts.dto.UserDTO;
import com.freitas.posts.repository.UserRepository;
import com.freitas.posts.service.exception.ObjNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author Edson da Silva Freitas
 * {@code @created} 23/08/2023
 * {@code @project} posts
 */
@Service
public class UserService {
    @Autowired
    private UserRepository repository;

    public List<UserDTO> findAll() {
        List<User> users = repository.findAll();
        return users.stream().map(UserDTO::new).collect(java.util.stream.Collectors.toList());
    }

    public User findById(String id) {
        return repository.findById(id).orElseThrow(() -> new ObjNotFoundException("Objeto não encontrado"));
        // O findOne() pode pode retornar um documento arbitrario se id nao existir, preferir usar o findById
    }

    public User insert(User user){
        return repository.insert(user);
        //Usar insert() sempre que for necessario apenas criar um documento novo
        //Usar save() ira atualizar o objeto se ja existir, caso contrario ira criar um novo
    }

    public User fromDTO (UserDTO dto){
        return new User(dto.getId(), dto.getName(), dto.getEmail());
    }

}