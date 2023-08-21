package com.api.hunter.vagas.controllers;

import com.api.hunter.vagas.dtos.AuthData;
import com.api.hunter.vagas.dtos.CreateUser;
import com.api.hunter.vagas.enums.Profile;
import com.api.hunter.vagas.models.User;
import com.api.hunter.vagas.repositories.UserRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
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
    private ObjectMapper json;

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


        userRepository.save(
                new User(
                        null,
                        userDto.name(),
                        userDto.email(),
                        userDto.password(),
                        userDto.company(),
                        userDto.profile()
                )
        );

        // when
        var response = mockMvc.perform(
                MockMvcRequestBuilders.get("/users")
        ).andReturn().getResponse();

        // then
        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
//        JsonAssert.with(response.getContentAsString()).extractingJsonPathArrayValue("$.items");









    }


    @Test
    @DisplayName("Deve cadastrar usuario admin")
    @WithMockUser
    void createAdmin() throws Exception {
        var createUserJson = json.writeValueAsString(new CreateUser(
                "Any name",
                "valid@email.com",
                "valid_password",
                Profile.ADMIN,
                null
        )
        );

        var response = mockMvc.perform(MockMvcRequestBuilders.post("/users")
                .contentType(MediaType.APPLICATION_JSON).content(createUserJson)
        ).andReturn().getResponse();


        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());

    }

    @Test
    @DisplayName("Deve retornar erro ao adicionar usuario admin vinculado a uma empresa")
    @WithMockUser
    void createAdminErrorCompany() throws Exception {
        var createUserJson = json.writeValueAsString(new CreateUser(
                        "Any name",
                        "valid@email.com",
                        "valid_password",
                        Profile.ADMIN,
                        "any_company"
                )
        );

        var response = mockMvc.perform(MockMvcRequestBuilders.post("/users")
                .contentType(MediaType.APPLICATION_JSON).content(createUserJson)
        ).andReturn().getResponse();


        assertThat(response.getStatus()).isEqualTo(HttpStatus.BAD_REQUEST.value());

    }

//  Recruiter

    @Test
    @DisplayName("Deve cadastrar usuario recruiter")
    @WithMockUser
    void createRecruiter() throws Exception {
        var createUserJson = json.writeValueAsString(new CreateUser(
                        "Any name",
                        "valid@email.com",
                        "valid_password",
                        Profile.RECRUITER,
                        "company_1"
                )
        );

        var response = mockMvc.perform(MockMvcRequestBuilders.post("/users")
                .contentType(MediaType.APPLICATION_JSON).content(createUserJson)
        ).andReturn().getResponse();


        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());

    }

    @Test
    @DisplayName("Deve retornar erro ao cadastrar usuario recruiter sem empresa")
    @WithMockUser
    void createRecruiterErrorCompany() throws Exception {
        var createUserJson = json.writeValueAsString(new CreateUser(
                        "Any name",
                        "valid@email.com",
                        "valid_password",
                        Profile.RECRUITER,
                        null
                )
        );

        var response = mockMvc.perform(MockMvcRequestBuilders.post("/users")
                .contentType(MediaType.APPLICATION_JSON).content(createUserJson)
        ).andReturn().getResponse();


        assertThat(response.getStatus()).isEqualTo(HttpStatus.BAD_REQUEST.value());

    }

    @Test
    @DisplayName("Deve retornar erro ao cadastrar usuario com email já cadastrado")
    @WithMockUser
    void createUserErrorEmail() throws Exception {

        var userDto = new CreateUser(
                "Any name",
                "valid@email.com",
                "valid_password",
                Profile.RECRUITER,
                "company_1"
        );
        var createUserJson = json.writeValueAsString(userDto);

        userRepository.save(
                new User(
                        null,
                        "new name",
                        userDto.email(),
                        userDto.password(),
                        userDto.company(),
                        userDto.profile()
                )
        );


        var response = mockMvc.perform(MockMvcRequestBuilders.post("/users")
                .contentType(MediaType.APPLICATION_JSON).content(createUserJson)
        ).andReturn().getResponse();


        assertThat(response.getStatus()).isEqualTo(HttpStatus.BAD_REQUEST.value());

    }


}