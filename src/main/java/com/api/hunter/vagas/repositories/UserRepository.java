package com.api.hunter.vagas.repositories;

import com.api.hunter.vagas.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.lang.NonNullApi;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.UUID;

public interface UserRepository extends JpaRepository<User, UUID> {

    boolean existsByEmail(String email);

    UserDetails findByEmail(String email);
    UserDetails getReferenceUserById(UUID id);
}
