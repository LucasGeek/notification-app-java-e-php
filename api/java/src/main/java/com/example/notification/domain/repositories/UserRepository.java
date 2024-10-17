package com.example.notification.domain.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.notification.domain.entities.User;

import java.util.Optional;
import java.util.UUID;

public interface UserRepository extends JpaRepository<User, UUID> {

    // Encontrar um usuário pelo email
    Optional<User> findByEmail(String email);
}