package com.api.hunter.vagas.controllers;

import com.api.hunter.vagas.dtos.CreateUser;
import com.api.hunter.vagas.dtos.UserDetail;
import com.api.hunter.vagas.enums.Profile;
import com.api.hunter.vagas.models.User;
import com.api.hunter.vagas.repositories.UserRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;

// TABELA VERDADE
// OR
// FALSE || TRUE  => TRUE
// FALSE || FALSE => FALSE
// TRUE  || FALSE => TRUE
// TRUE  || TRUE  => TRUE

// AND
// FALSE && TRUE  => FALSE
// FALSE && FALSE => FALSE
// TRUE  && FALSE => FALSE
// TRUE  && TRUE  => TRUE

@RestController
@RequestMapping("/users")
public class UserController {
    @Autowired
    private UserRepository userRepository;

    @PostMapping
    public ResponseEntity createUser(@RequestBody @Valid CreateUser newUser){
        System.out.println(newUser);
        return ResponseEntity.ok().build();
    }

    @GetMapping
    public ResponseEntity<List<User>> list(){
        var users = userRepository.findAll();
        return ResponseEntity.ok(users);
    }

}
