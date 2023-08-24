package com.freitas.posts.dto;

import com.freitas.posts.domain.User;

import java.io.Serializable;

/**
 * @author Edson da Silva Freitas
 * {@code @created} 24/08/2023
 * {@code @project} posts
 */
public class AuthorDTO implements Serializable {
    private static final long serialVersionUID = 1L;
    private String id;
    private String name;

    public AuthorDTO() {
    }

    public AuthorDTO(User obj) {
        if (obj.getId() != null) this.id = obj.getId();
        if (obj.getName() != null) this.name = obj.getName();
    }

    public AuthorDTO(String id, String name) {
        this.id = id;
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}