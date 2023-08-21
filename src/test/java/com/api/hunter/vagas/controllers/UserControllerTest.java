package com.api.hunter.vagas.controllers;

import com.api.hunter.vagas.dtos.AuthData;
import com.api.hunter.vagas.dtos.CreateUser;
import com.api.hunter.vagas.enums.Profile;
import com.api.hunter.vagas.models.User;
import com.api.hunter.vagas.repositories.UserRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.ArrayList;
import java.util.Collections;

import static org.assertj.core.api.Assertions.assertThat;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ActiveProfiles("Test")
@AutoConfigureMockMvc()
class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private UserRepository userRepository;

    @AfterEach
    public void afterEach() {
        userRepository.deleteAll();
    }

    @Test
    @DisplayName("Deve retornar uma lista vazia")
    @WithMockUser
    void listCase1() throws Exception {
        // given

        // when
        var response = mockMvc.perform(
                MockMvcRequestBuilders.get("/users")
        ).andReturn().getResponse();

        // then
        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
        assertThat(response.getContentAsString()).isEqualTo("[]");

    }

    @Test
    @DisplayName("Deve retornar uma lista com uma pessoa cadastrada")
    @WithMockUser
    void listCase2() throws Exception {
        // given
        var userDto = new CreateUser(
                "Any name",
                "any@email.com",
                "any_password",
                Profile.ADMIN,
                "Any company"
        );


//        userRepository.save(
//                new User(
//                        null,
//                        userDto.name(),
//                        userDto.email(),
//                        userDto.password(),
//                        userDto.company(),
//                        userDto.profile()
//                )
//        );

        // when
        var response = mockMvc.perform(
                MockMvcRequestBuilders.get("/users")
        ).andReturn().getResponse();

        // then
        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
        var arrayResponse = new ArrayList<>(Collections.singleton(response.getContentAsByteArray()));
        assertThat(arrayResponse.size()).isEqualTo(1);

    }


}