package org.example.test_task_comparus.controller;

import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.example.test_task_comparus.dto.UserDTO;
import org.example.test_task_comparus.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public class UserController {
    UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }


    @GetMapping("/users")
    public ResponseEntity<?> getUsers(@RequestParam(required = false) String username,
                                      @RequestParam(required = false) String name,
                                      @RequestParam(required = false) String surname) {
        List<UserDTO> all = userService.findAllWithFilter(username, name, surname);
        return new ResponseEntity<>(all, HttpStatus.OK);
    }
}
