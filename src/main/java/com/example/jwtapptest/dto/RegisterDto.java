package com.example.jwtapptest.dto;

import com.example.jwtapptest.model.User;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
@Data
public class RegisterDto {
    private String username;
    private String firstName;
    private String lastName;
    private String email;
    private String password;

    public User toUser() {
        User user = new User();
        user.setUsername(username);
        user.setFirstName(firstName);
        user.setLastName(lastName);
        user.setEmail(email);
        user.setPassword(password);
        return user;
    }
}
