package com.freitas.posts.resource.util;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;

/**
 * Lida com espaço no requestParam do PostResource
 * @author Edson da Silva Freitas
 * {@code @created} 25/08/2023
 * {@code @project} posts
 */
public class DecoderURLParam {

    public static String decodeParam(String text) {
        try {
            return URLDecoder.decode(text, StandardCharsets.UTF_8.toString());
        } catch (UnsupportedEncodingException e) {
            return " ";
        }
    }
}