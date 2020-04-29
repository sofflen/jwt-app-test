package com.example.jwtapptest.controller;

import com.example.jwtapptest.dto.AuthenticationRequestDto;
import com.example.jwtapptest.dto.RegisterDto;
import com.example.jwtapptest.model.User;
import com.example.jwtapptest.security.jwt.JwtTokenProvider;
import com.example.jwtapptest.service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
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
    @MockBean
    private AuthenticationManager manager;
    @MockBean
    private JwtTokenProvider tokenProvider;
    @MockBean
    private BCryptPasswordEncoder passwordEncoder;


    private RegisterDto registerDto;
    private AuthenticationRequestDto requestDto;
    private User registerUser;
    private String uri;

    @BeforeEach
    void setup() {
        registerDto = new RegisterDto("DJ", "Andrey", "Balkonsky", "napishite@mail.ru", "bass");
        requestDto = new AuthenticationRequestDto("DJ", "bass");
        registerUser = registerDto.toUser();
        uri = "/api/v1/auth";
    }

    @Test
    void register() throws Exception {
        when(userService.register(registerUser)).thenReturn(registerUser);

        mvc.perform(post(uri + "/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(registerDto)))
                .andDo(print())
                .andExpect(status().isOk());

        verify(userService).register(registerUser);
    }

    @Test
    void login() throws Exception {
        when(userService.findByUsername(requestDto.getUsername())).thenReturn(registerDto.toUser());


        mvc.perform(post(uri + "/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(requestDto)))
                .andDo(print())
                .andExpect(status().isOk());

        verify(tokenProvider).createToken(requestDto.getUsername(),registerDto.toUser().getRoles());

    }
}