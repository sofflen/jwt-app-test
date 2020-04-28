package com.example.jwtapptest.controller;

import com.example.jwtapptest.dto.UserDto;
import com.example.jwtapptest.service.UserService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class UserRestControllerTest {

    @Autowired
    private MockMvc mvc;

    @Autowired
    ObjectMapper mapper;

    @MockBean
    private UserService userService;

    private UserDto user;
    private String userJson;
    private String uri;

    @BeforeEach
    void setup() throws JsonProcessingException {
        user = new UserDto(1L, "DJ", "Andrey", "Balkonsky","napishite@mail.ru");
        userJson = mapper.writeValueAsString(user);
        uri = "/api/v1/users";
    }

    @Test
    void getUserById_existingUser() throws Exception {

        when(userService.findById(1L)).thenReturn(user.toUser());

        mvc.perform(get(uri + "/{id}", 1)
                .with(user("registerBoi").password("rega"))
                .contentType(APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().json(userJson));

        verify(userService).findById(1L);
    }

    @Test
    void getUserById_nullUser() throws Exception {
        when(userService.findById(100L)).thenReturn(null);

        mvc.perform(get(uri + "/{id}", 100)
                .with(user("registerBoi").password("rega"))
                .contentType(APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isNoContent());

        verify(userService).findById(100L);
    }
}