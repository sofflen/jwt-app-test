package com.example.jwtapptest.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class AuthenticationRequestDto {

    private String username;
    private String password;
}
