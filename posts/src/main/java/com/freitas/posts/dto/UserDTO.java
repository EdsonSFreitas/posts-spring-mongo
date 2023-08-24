package com.freitas.posts.dto;

import com.freitas.posts.domain.User;

import java.io.Serializable;

/**
 * @author Edson da Silva Freitas
 * {@code @created} 23/08/2023
 * {@code @project} posts
 */
public class UserDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    private String id;
    private String name;
    private String email;

    public UserDTO(User dados) {
        this.id = dados.getId();
        this.name = dados.getName();
        this.email = dados.getEmail();
    }

    public UserDTO() {
    }

    public UserDTO(String id, String name, String email) {
        this.id = id;
        this.name = name;
        this.email = email;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }
}