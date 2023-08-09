package com.api.hunter.vagas.controllers;

import com.api.hunter.vagas.dtos.AuthData;
import com.api.hunter.vagas.dtos.OutputLogin;
import com.api.hunter.vagas.models.User;
import com.api.hunter.vagas.services.TokenService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("login")
public class AuthController {

    @Autowired
    private AuthenticationManager manager;

    @Autowired
    private TokenService tokenService;

    @PostMapping
    public ResponseEntity doLogin(@RequestBody @Valid AuthData data){
        var token = new UsernamePasswordAuthenticationToken(data.email(), data.pass());
        var authentication = manager.authenticate(token);
        var tokenJWT = tokenService.generateToken((User) authentication.getPrincipal());

        return ResponseEntity.ok().body(new OutputLogin(tokenJWT));
    }
}
