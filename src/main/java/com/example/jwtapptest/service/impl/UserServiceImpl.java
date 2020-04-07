package com.example.jwtapptest.service.impl;

import com.example.jwtapptest.model.Role;
import com.example.jwtapptest.model.Status;
import com.example.jwtapptest.model.User;
import com.example.jwtapptest.repository.RoleRepository;
import com.example.jwtapptest.repository.UserRepository;
import com.example.jwtapptest.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    public UserServiceImpl(UserRepository userRepository, RoleRepository roleRepository, BCryptPasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public User register(User user) {
        Role roleUser = roleRepository.findByName("role_user");
        List<Role> userRoles = new ArrayList<>();
        userRoles.add(roleUser);

        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setRoles(userRoles);
        user.setStatus(Status.ACTIVE);

        User registeredUser = userRepository.save(user);

        log.info("IN register - user {} successfully registered", registeredUser);

        return registeredUser;
    }

    @Override
    public List<User> getAll() {
        List<User> users = userRepository.findAll();
        log.info("in getAll - {} users found", users.size());
        return users;
    }

    @Override
    public User findByUsername(String username) {
        User user = userRepository.findByUsername(username);
        log.info("IN findByUsername - user {} found by username \"{}\"", user, username);
        return user;
    }

    @Override
    public User findById(long id) {
        User user = userRepository.findById(id).orElse(null);

        if (user == null) {
            log.warn("IN findById - no user found by ID {}", id);
            return null;
        }
        log.info("IN findById - user {} found by ID {}", user, id);
        return user;
    }

    @Override
    public void delete(Long id) {
        User user = userRepository.findById(id).orElse(null);

        if (user == null) {
            log.warn("IN findById - no user found by ID {}", id);
            return;
        }

        user.setStatus(Status.DELETED);
        userRepository.save(user);
        log.info("IN delete - user {} found by ID {} was deleted", user, id);
    }
}
