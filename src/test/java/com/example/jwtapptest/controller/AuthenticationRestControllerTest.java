package com.example.jwtapptest.controller;

import com.example.jwtapptest.dto.RegisterDto;
import com.example.jwtapptest.model.User;
import com.example.jwtapptest.service.UserService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class AuthenticationRestControllerTest {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private ObjectMapper mapper;

    @MockBean
    private UserService userService;


    private RegisterDto registerUserDto;
    private User registerUser;
    private String registerUserDtoJson;
    private String uri;

    @BeforeEach
    void setup() throws JsonProcessingException {
        registerUserDto = new RegisterDto("DJ","Andrey","Balkonsky","napishite@mail.ru","bass");
        registerUser = registerUserDto.toUser();
        registerUserDtoJson = mapper.writeValueAsString(registerUserDto);
        uri = "/api/v1/auth";
    }

    @Test
    void register() throws Exception {
        when(userService.register(registerUser)).thenReturn(registerUser);

        mvc.perform(MockMvcRequestBuilders.post(uri + "/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(registerUserDtoJson))
                .andDo(print())
                .andExpect(status().isOk());

        verify(userService).register(registerUser);
    }

    @Test
    void login() {
    }
}