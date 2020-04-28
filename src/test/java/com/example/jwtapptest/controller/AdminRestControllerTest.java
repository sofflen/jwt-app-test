package com.example.jwtapptest.controller;

import com.example.jwtapptest.dto.AdminUserDto;
import com.example.jwtapptest.model.Role;
import com.example.jwtapptest.service.UserService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Collections;

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
    class AdminRestControllerTest {

        @Autowired
        private MockMvc mvc;

        @Autowired
        ObjectMapper mapper;

        @MockBean
        private UserService userService;

        private AdminUserDto admin;
        private String adminJson;
        private String uri;
        private Role adminRole;

        @BeforeEach
        void setup() throws JsonProcessingException {
            adminRole = new Role();
            adminRole.setName("ROLE_admin");
            admin = new AdminUserDto(1L,"DJ", "Andrey", "Balkonsky", "napishite@mail.ru","ACTIVE", Collections.singletonList(adminRole));
            adminJson = mapper.writeValueAsString(admin);
            uri = "/api/v1/admin";
        }

        @Disabled
        @Test
        void getUserById_existingUser() throws Exception {

            when(userService.findById(1L)).thenReturn(admin.toUser());

            mvc.perform(get(uri + "/users/{id}", 1)
                    .with(user("registerBoi").password("rega"))
                    .contentType(APPLICATION_JSON))
                    .andDo(print())
                    .andExpect(status().isOk())
                    .andExpect(content().json(adminJson));

            verify(userService).findById(1L);
        }
}