package com.freitas.posts.config;

import com.freitas.posts.domain.Post;
import com.freitas.posts.domain.User;
import com.freitas.posts.dto.AuthorDTO;
import com.freitas.posts.dto.UserDTO;
import com.freitas.posts.repository.PostRepository;
import com.freitas.posts.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author Edson da Silva Freitas
 * {@code @created} 23/08/2023
 * {@code @project} posts
 */
@Configuration
@Profile("dev")
public class TestConfig implements CommandLineRunner {
    @Autowired
    private UserRepository repository;
    @Autowired
    private PostRepository postRepository;


    @Override
    public void run(String... args) throws Exception {

        repository.deleteAll();
        User u1 = new User(null, "Maria Brown", "maria@gmail.com");
        User u2 = new User(null, "Alex Green", "alex@gmail.com");
        User u3 = new User(null, "Bob Grey", "bob@gmail.com");
        User u4 = new User(null, "Ruby", "ruby@gmail.com");
        User u5 = new User(null, "Maribel", "maribel@gmail.com");
        repository.saveAll(List.of(u1, u2, u3, u4, u5));

        postRepository.deleteAll();
        Post post1 = new Post(null, LocalDate.now(), "Partiu viagem", "Vou viajar sem data pra voltar. Abraços!", new AuthorDTO(u1));
        Post post2 = new Post(null, LocalDate.now(), "Bom dia", "Hoje será um grande dia!", new AuthorDTO(u1));
        Post post3 = new Post(null, LocalDate.now(), "Boa tarde", "Hoje será uma grande tarde!", new AuthorDTO(u2));
        Post post4 = new Post(null, LocalDate.now(), "Olá", "Qualé?", new AuthorDTO(u3));
        Post post5 = new Post(null, LocalDate.now(), "Mais um dia pra conta", "Devagar e sempre", new AuthorDTO(u3));
        postRepository.saveAll(List.of(post1, post2, post3, post4, post5));

        User u6 = new User(null, "Freitas", "freitas@gmail.com");
        repository.save(u6); //Persistindo usuario para que receba o id antes de persistir o post devido a relacao com o author
        Post post6 = new Post(null, LocalDate.now(), "Spring com MongoDB", "Estudando todo dia", new AuthorDTO(u6));
        postRepository.saveAll(List.of(post6));
    }
}