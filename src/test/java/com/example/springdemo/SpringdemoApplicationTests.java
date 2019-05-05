package com.example.springdemo;

import com.example.springdemo.configs.CustomTokenEnhancer;
import com.example.springdemo.configs.OAuth2AuthorizationServerConfig;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.junit.Assert.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest(
        webEnvironment = SpringBootTest.WebEnvironment.MOCK,
        classes = {SpringdemoApplication.class, OAuth2AuthorizationServerConfig.class, CustomTokenEnhancer.class})
@AutoConfigureMockMvc
@TestPropertySource(locations = "classpath:application.yml")
@ActiveProfiles("test")
public class SpringdemoApplicationTests {

    @Autowired
    private MockMvc mvc;


    @Test
    @SneakyThrows
    public void contextLoads() {
//        grant_type with the value password
//        client_id with the the client’s ID
//        client_secret with the client’s secret
//        scope with a space-delimited list of requested scope permissions.
//                username with the user’s username
//        password with the user’s password

        Map<String, String> content = Stream.of(new Object[][]{
                {"grant_type", "password"},
                {"client_id", "fooClientIdPassword"},
                {"client_", "secret"},
                {"username", "pippo"},
                {"password", "123"},
                {"scope", "read"},
        }).collect(Collectors.toMap(data -> (String) data[0], data -> (String) data[1]));


        Map<String, Object> authorizeRequest = Stream.of(new Object[][]{
                {"approvalParameters", content},
        }).collect(Collectors.toMap(data -> (String) data[0], data -> (Object) data[1]));

        ObjectMapper mapper = new ObjectMapper();

        String authorizeJson = mapper.writeValueAsString(content);

        mvc.perform(post("/oauth/token")
                .content(authorizeJson)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content()
                        .contentTypeCompatibleWith(MediaType.APPLICATION_JSON));
//                    .andExpect(jsonPath("$[0].name", is("bob")));

        assertTrue(true);
    }
}

