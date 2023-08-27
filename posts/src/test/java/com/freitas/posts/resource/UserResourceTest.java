package com.freitas.posts.resource;

import com.freitas.posts.PostsApplication;
import com.freitas.posts.dto.UserDTO;
import com.freitas.posts.service.UserService;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.servlet.View;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.view.json.MappingJackson2JsonView;

import java.util.Arrays;
import java.util.List;
import java.util.Locale;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@SpringBootTest(properties = {"spring.profiles.active=test"}, classes = PostsApplication.class)
@AutoConfigureMockMvc
//@ExtendWith(SpringExtension.class)
class UserResourceTest {

    @Autowired
    private UserResource userResource;

    @Mock
    private UserService userService;

    private MockMvc mockMvc;

    @BeforeEach
    public void setUp() {
        mockMvc = MockMvcBuilders
                .standaloneSetup(userResource)
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
        // Define a lista de UserDTO que você espera na resposta
        List<UserDTO> expectedUsers = Arrays.asList(
                new UserDTO("64e8f66a021d400766c2f102", "Maria Brown", "maria@gmail.com"),
                new UserDTO("64e8f66a021d400766c2f103", "Alex Green", "alex@gmail.com")
        );

        // Define o objeto Page que simula a resposta com paginação
        Page<UserDTO> page = new PageImpl<>(expectedUsers);

        // Mock o comportamento do serviço para retornar a página
        when(userService.findAll(any(Pageable.class))).thenReturn(page);

        // Execute a solicitação GET para /users?page=0&size=2&sort=string
        mockMvc.perform(MockMvcRequestBuilders.get("/users?page=0&size=2&sort=string"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$.content").isArray())
                .andExpect(MockMvcResultMatchers.jsonPath("$.content", Matchers.hasSize(expectedUsers.size())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.content[0].id").value(expectedUsers.get(0).getId()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.content[0].name").value(expectedUsers.get(0).getName()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.content[0].email").value(expectedUsers.get(0).getEmail()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.content[1].id").value(expectedUsers.get(1).getId()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.content[1].name").value(expectedUsers.get(1).getName()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.content[1].email").value(expectedUsers.get(1).getEmail()));
    }

}