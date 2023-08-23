package com.freitas.posts.service.exception;

/**
 * @author Edson da Silva Freitas
 * {@code @created} 23/08/2023
 * {@code @project} posts
 */
public class ObjNotFoundException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public ObjNotFoundException(String message) {
        super(message);
    }
}