package com.freitas.posts.resource;


import com.freitas.posts.PostsApplication;
import com.freitas.posts.domain.User;
import com.freitas.posts.dto.AuthorDTO;
import com.freitas.posts.dto.PostDTO;
import com.freitas.posts.service.PostService;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.servlet.View;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.view.json.MappingJackson2JsonView;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@SpringBootTest(properties = {"spring.profiles.active=test"}, classes = PostsApplication.class)
@AutoConfigureMockMvc
//@ExtendWith(SpringExtension.class)
class PostResourceTest {
    @Autowired
    private PostResource postResource;

    @Mock
    private PostService postService;

    private MockMvc mockMvc;

    @BeforeEach
    public void setUp() {
        //   MockitoAnnotations.initMocks(this);
        mockMvc = MockMvcBuilders
                .standaloneSetup(postResource)
                .setCustomArgumentResolvers(new PageableHandlerMethodArgumentResolver())
                .setViewResolvers(new ViewResolver() {
                    @Override
                    public View resolveViewName(String viewName, Locale locale) throws Exception {
                        return new MappingJackson2JsonView();
                    }
                })
                .build();
    }

    @Test
    void testFindAll() throws Exception {

        User u1 = new User(null, "Maria Brown", "maria@gmail.com");
        User u2 = new User(null, "Alex Green", "alex@gmail.com");

        // Define a lista de PostDTO que você espera na resposta
        List<PostDTO> expectedUsers = Arrays.asList(
                new PostDTO("64ea69ef38031f486ab9e79a", LocalDateTime.now(), "Partiu viagem", "Vou viajar sem data pra voltar. Abraços!", new AuthorDTO(u1)),
                new PostDTO("64ea69ef38031f486ab9e79b", LocalDateTime.now(), "Bom dia", "Hoje será um grande dia!", new AuthorDTO(u2))
        );

        // Define o objeto Page que simula a resposta com paginação
        Page<PostDTO> page = new PageImpl<>(expectedUsers);

        // Mock o comportamento do serviço para retornar a página
        when(postService.findAll(any(Pageable.class))).thenReturn(page);

        // Execute a solicitação GET para /posts?page=0&size=2&sort=id
        mockMvc.perform(MockMvcRequestBuilders.get("/posts?page=0&size=2&sort=id"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$.content").isArray())
                .andExpect(MockMvcResultMatchers.jsonPath("$.content", Matchers.hasSize(expectedUsers.size())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.content[0].id").value(expectedUsers.get(0).getId()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.content[0].title").value(expectedUsers.get(0).getTitle()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.content[0].body").value(expectedUsers.get(0).getBody()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.content[1].id").value(expectedUsers.get(1).getId()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.content[1].title").value(expectedUsers.get(1).getTitle()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.content[1].body").value(expectedUsers.get(1).getBody()));
    }
}