package com.api.hunter.vagas.controllers;

import com.api.hunter.vagas.dtos.CreateUser;
import com.api.hunter.vagas.dtos.ErrorData;
import com.api.hunter.vagas.dtos.UserDetail;
import com.api.hunter.vagas.enums.Profile;
import com.api.hunter.vagas.models.User;
import com.api.hunter.vagas.repositories.UserRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.crypto.password.PasswordEncoder;
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

    @Autowired
    private PasswordEncoder passwordEncoder;

    @PostMapping
    @Transactional
    public ResponseEntity createAdminOrRecruiter(@AuthenticationPrincipal User userLogged, @RequestBody @Valid CreateUser newUser) {
        System.out.println("Usuario Autenticado" + userLogged.getName());
        if (newUser.profile().equals(Profile.RECRUITER)) {
            if (newUser.company() == null || newUser.company().isEmpty()) {
                return  ResponseEntity.badRequest().body(
                        new ErrorData("company", "Campo obrigatório. Todo usuário do perfil RECRUITER precisa ter uma empresa")
                );
            }
        } else {
            if(newUser.company() != null) {
                return ResponseEntity.badRequest().body(new ErrorData("company", "Campo desnecessário. Usuário do perfil ADMIN não podem ter uma empresa vinculada."));
            }
        }

        if(userRepository.existsByEmail(newUser.email())) {
            return ResponseEntity.badRequest().body(new ErrorData("email", "Este e-mail já foi cadastrado!"));
        }

        var user = new User(
                null,
                newUser.name(),
                newUser.email(),
                passwordEncoder.encode(newUser.password()),
                newUser.company(),
                newUser.profile()
        );

        userRepository.save(user);

        return ResponseEntity.ok(new UserDetail(user));
    }

    @GetMapping
    public ResponseEntity<List<User>> list(){
        var users = userRepository.findAll();
        return ResponseEntity.ok(users);
    }

}
