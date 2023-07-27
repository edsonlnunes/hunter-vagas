package com.api.hunter.vagas.dtos;

import com.api.hunter.vagas.enums.Profile;
import com.api.hunter.vagas.models.User;

import java.util.UUID;

public record UserDetail(
        UUID id,
        String name,
        String email,
        Profile profile,
        String company
) {
    public UserDetail(User user){
        this(user.getId(), user.getName(), user.getEmail(), user.getProfile(), user.getCompany());
    }
}
