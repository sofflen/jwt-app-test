package com.example.jwtapptest.controller;

import com.example.jwtapptest.dto.UserDto;
import com.example.jwtapptest.model.User;
import com.example.jwtapptest.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import java.io.File;

@RestController
@RequestMapping(value = "/api/v1/users")
public class UserRestController {

    private final UserService userService;

    public UserRestController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping(value = "{id}")
    public ResponseEntity<UserDto> getUserById(@PathVariable(name = "id") Long id) {
        User user = userService.findById(id);

        if (user == null)
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);

        UserDto result = UserDto.fromUser(user);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @PostMapping("/marshal")
    public ResponseEntity<UserDto> marshal(@RequestBody UserDto userDto) throws JAXBException {
        JAXBContext context = JAXBContext.newInstance(UserDto.class);
        Marshaller marshaller = context.createMarshaller();
        marshaller.marshal(new UserDto(userDto.getId(),userDto.getUsername(), userDto.getFirstName(), userDto.getLastName(), userDto.getEmail()), new File(String.format("%s.xml", userDto.getUsername())));
        return ResponseEntity.ok(userDto);
    }

    @GetMapping("/unmarshal/{username}")
    public ResponseEntity<Object> unmarshal(@PathVariable String username) {
        try {
            JAXBContext context = JAXBContext.newInstance(UserDto.class);
            Unmarshaller unmarshaller = context.createUnmarshaller();
            Object user = unmarshaller.unmarshal(new File(String.format("%s.xml", username)));
            return new ResponseEntity<>(user, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
    }
}
