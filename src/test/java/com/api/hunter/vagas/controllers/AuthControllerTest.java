package com.api.hunter.vagas.controllers;

import com.api.hunter.vagas.dtos.AuthData;
import com.api.hunter.vagas.dtos.CreateUser;
import com.api.hunter.vagas.enums.Profile;
import com.api.hunter.vagas.models.User;
import com.api.hunter.vagas.repositories.UserRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import static org.assertj.core.api.Assertions.assertThat;


@SpringBootTest
@ActiveProfiles("Test")
@AutoConfigureMockMvc()
class AuthControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper json;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @AfterEach
    public void afterEach() {
        userRepository.deleteAll();
    }

    @Test
    @DisplayName("Deve retornar token quando as informações estiverem corretas")
    void doLogin1() throws Exception {
        // given
        var userDto = new CreateUser(
                "Any name",
                "valid@email.com",
                "valid_password",
                Profile.ADMIN,
                "Any company"
        );

        var dataJson = json.writeValueAsString(
                new AuthData(
                        userDto.email(),
                        userDto.password()
                )
        );

        userRepository.save(
                new User(
                        null,
                        userDto.name(),
                        userDto.email(),
                        passwordEncoder.encode(userDto.password()),
                        userDto.company(),
                        userDto.profile()
                )
        );

        // when
        var response = mockMvc.perform(
                MockMvcRequestBuilders.post("/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(dataJson)
        ).andReturn().getResponse();

        //then
        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
    }

    @Test
    @DisplayName("Deve retornar status forbidden quando informar senha incorreta")
    void doLogin2() throws Exception {
        // given
        var userDto = new CreateUser(
                "Any name",
                "valid@email.com",
                "valid_password",
                Profile.ADMIN,
                "Any company"
        );

        var dataJson = json.writeValueAsString(
                new AuthData(
                        userDto.email(),
                        "invalid_password"
                )
        );

        userRepository.save(
                new User(
                        null,
                        userDto.name(),
                        userDto.email(),
                        passwordEncoder.encode(userDto.password()),
                        userDto.company(),
                        userDto.profile()
                )
        );

        // when
        var response = mockMvc.perform(
                MockMvcRequestBuilders.post("/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(dataJson)
        ).andReturn().getResponse();

        //then
        assertThat(response.getStatus()).isEqualTo(HttpStatus.FORBIDDEN.value());
    }

    @Test
    @DisplayName("Deve retornar status forbidden quando informar email incorreto")
    void doLogin3() throws Exception {
        // given
        var userDto = new CreateUser(
                "Any name",
                "valid@email.com",
                "valid_password",
                Profile.ADMIN,
                "Any company"
        );

        var dataJson = json.writeValueAsString(
                new AuthData(
                        "invalid@email.com",
                        userDto.password()
                )
        );

        userRepository.save(
                new User(
                        null,
                        userDto.name(),
                        userDto.email(),
                        passwordEncoder.encode(userDto.password()),
                        userDto.company(),
                        userDto.profile()
                )
        );

        // when
        var response = mockMvc.perform(
                MockMvcRequestBuilders.post("/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(dataJson)
        ).andReturn().getResponse();

        //then
        assertThat(response.getStatus()).isEqualTo(HttpStatus.FORBIDDEN.value());
    }





}