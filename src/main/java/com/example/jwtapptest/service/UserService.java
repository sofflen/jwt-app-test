package com.example.jwtapptest.service;

import com.example.jwtapptest.model.User;

import java.util.List;

public interface UserService {

    User register(User user);

    List<User> getAll();

    User findByUsername(String username);

    User findById(long id);

    void delete(Long id);
}
