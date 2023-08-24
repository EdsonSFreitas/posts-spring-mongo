package com.freitas.posts.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

/**
 * Credencias de acesso ao banco a partir de variaveis de ambiente no IntelliJ
 * @author Edson da Silva Freitas
 * {@code @created} 24/08/2023
 * {@code @project} posts
 */
@Configuration
public class EnvironmentConfig {
    @Value("${DB_USER}")
    private String dbUser;

    @Value("${DB_PASS}")
    private String dbPassword;
}