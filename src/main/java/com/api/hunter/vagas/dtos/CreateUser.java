package com.api.hunter.vagas.dtos;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import org.hibernate.validator.constraints.Length;

/*
  name: string (obrigatório),
  email: string (obrigatório),
  password: string (obrigatório),
  profile: string [RECRUITER, ADMIN] (obrigatório),
  company: string (opcional)
 */
public record CreateUser(
        @NotBlank
        @Length(min = 3, max = 100)
        String name,
        @NotBlank
        @Email
        String email,
        @NotBlank
        @Length(min = 6)
        String password,
        @NotBlank
        String profile,
        String company
) { }
