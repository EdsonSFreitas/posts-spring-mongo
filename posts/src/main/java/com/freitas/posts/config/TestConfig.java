package com.freitas.posts.config;

import com.freitas.posts.domain.Post;
import com.freitas.posts.domain.User;
import com.freitas.posts.dto.AuthorDTO;
import com.freitas.posts.dto.CommentDTO;
import com.freitas.posts.repository.PostRepository;
import com.freitas.posts.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import java.time.LocalDateTime;
import java.util.Arrays;

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
        User u1 = new User("64e8f66a021d400766c2f102", "Maria Brown", "maria@gmail.com");
        User u2 = new User("64e8f66a021d400766c2f103", "Alex Green", "alex@gmail.com");
        User u3 = new User(null, "Bob Grey", "bob@gmail.com");
        User u4 = new User(null, "Ruby", "ruby@gmail.com");
        User u5 = new User(null, "Maribel", "maribel@gmail.com");
        repository.saveAll(Arrays.asList(u1, u2, u3, u4, u5));

        postRepository.deleteAll();
        Post post1 = new Post("64ea69ef38031f486ab9e79a", LocalDateTime.now(), "Partiu viagem", "Vou viajar sem data pra voltar. Abraços!", new AuthorDTO(u1));
        Post post2 = new Post("64ea69ef38031f486ab9e79b", LocalDateTime.now(), "Bom dia", "Hoje será um grande dia!", new AuthorDTO(u1));
        Post post3 = new Post(null, LocalDateTime.now(), "Boa tarde", "Hoje será uma grande tarde!", new AuthorDTO(u2));
        Post post4 = new Post(null, LocalDateTime.now(), "Olá", "Qualé?", new AuthorDTO(u3));
        Post post5 = new Post(null, LocalDateTime.now(), "Mais um dia pra conta", "Devagar e sempre", new AuthorDTO(u3));
        postRepository.saveAll(Arrays.asList(post1, post2, post3, post4, post5));

        //Persiste usuario, persiste post e em outro bloco adiciona o post na lista do usuario
        User u6 = new User(null, "Freitas", "freitas@gmail.com");
        repository.save(u6); //Persistindo usuario para que receba o id antes de persistir o post devido a relacao com o author
        Post post6 = new Post(null, LocalDateTime.now(), "Spring boot com MongoDB", "Estudando 10h/dia", new AuthorDTO(u6));
        postRepository.save(post6);

        //Add posts aos usuarios
        u1.addPostsAll(Arrays.asList(post1, post2, post3, post4));
        u2.addPosts(post5);
        u3.addPostsAll(Arrays.asList(post1, post2, post3, post4));
        u6.addPosts(post6);
        repository.saveAll(Arrays.asList(u1, u2, u3, u6));

        //Add comentarios aos posts
        CommentDTO c1 = new CommentDTO("Boa viagem mano!", LocalDateTime.of(2020, 1, 25, 0, 0), new AuthorDTO(u1));
        CommentDTO c2 = new CommentDTO("Aproveite", LocalDateTime.now(), new AuthorDTO(u2));
        CommentDTO c3 = new CommentDTO("Tenha um ótimo dia!", LocalDateTime.now(), new AuthorDTO(u3));
        CommentDTO c4 = new CommentDTO("Você consegue!", LocalDateTime.now(), new AuthorDTO(u4));
        CommentDTO c5 = new CommentDTO("Continua...!", LocalDateTime.now(), new AuthorDTO(u5));
        post1.addCommentAll(Arrays.asList(c1, c2));
        post2.addCommentAll(Arrays.asList(c3));
        post6.addCommentAll(Arrays.asList(c4, c5));
        postRepository.saveAll(Arrays.asList(post1, post2, post6));

    }
}