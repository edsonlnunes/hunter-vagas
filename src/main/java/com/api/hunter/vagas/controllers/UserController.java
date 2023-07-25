package com.api.hunter.vagas.controllers;

import com.api.hunter.vagas.dtos.CreateUser;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/users")
public class UserController {
    @PostMapping
    public ResponseEntity createUser(@RequestBody @Valid CreateUser newUser){
        System.out.println(newUser);
        return ResponseEntity.ok().build();
    }
}
