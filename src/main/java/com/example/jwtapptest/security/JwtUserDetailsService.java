package com.example.jwtapptest.security;

import com.example.jwtapptest.model.User;
import com.example.jwtapptest.repository.UserRepository;
import com.example.jwtapptest.security.jwt.JwtUser;
import com.example.jwtapptest.security.jwt.JwtUserFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class JwtUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    public JwtUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username);

        if (user == null)
            throw new UsernameNotFoundException("Username " + username + " not found");

        JwtUser jwtUser = JwtUserFactory.create(user);
        log.info("IN loadUserByUsername - user {} successfully loaded", username);
        return jwtUser;
    }
}
