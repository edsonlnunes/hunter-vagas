package com.api.hunter.vagas.controllers;

import com.api.hunter.vagas.builders.dtos.CreateUserBuilder;
import com.api.hunter.vagas.builders.dtos.UserBuilder;
import com.api.hunter.vagas.enums.Profile;
import com.api.hunter.vagas.repositories.UserRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;


import static org.assertj.core.api.Assertions.assertThat;

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



    @Autowired
    private PasswordEncoder passwordEncoder;

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

        var userDto = CreateUserBuilder.init().builder();

        userRepository.save(
                UserBuilder.init().withPassword(passwordEncoder.encode("valid_password")).builder()

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

        var createUserJson = json.writeValueAsString(
                CreateUserBuilder.init().builder()

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

        var createUserJson = json.writeValueAsString(
                CreateUserBuilder.init().withCompany("any_company").builder()
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
        var createUserJson = json.writeValueAsString(
                CreateUserBuilder.init().withCompany("company_1").withProfile(Profile.RECRUITER).builder()

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

        var createUserJson = json.writeValueAsString(
                CreateUserBuilder.init().withProfile(Profile.RECRUITER).builder()

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

        var userDto = CreateUserBuilder.init().builder();

        var createUserJson = json.writeValueAsString(userDto);

        userRepository.save(
                UserBuilder.init().withPassword(passwordEncoder.encode("valid_password")).builder()

        );


        var response = mockMvc.perform(MockMvcRequestBuilders.post("/users")
                .contentType(MediaType.APPLICATION_JSON).content(createUserJson)
        ).andReturn().getResponse();


        assertThat(response.getStatus()).isEqualTo(HttpStatus.BAD_REQUEST.value());

    }


}