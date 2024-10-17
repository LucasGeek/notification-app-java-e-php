package com.example.notification.api.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;

import com.example.notification.domain.entities.User;
import com.example.notification.infrastructure.security.UserAuthentication;

public abstract class BaseController {

    protected User getAuthenticatedUser(Authentication authentication) {
        if (!(authentication instanceof UserAuthentication userAuth)) {
            return null;
        }

        return userAuth.getUser();
    }

    protected ResponseEntity<?> validateAuthenticatedUser(Authentication authentication) {
        if (!(authentication instanceof UserAuthentication)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Token inválido ou usuário não autenticado.");
        }

        User user = getAuthenticatedUser(authentication);
        if (user == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Usuário não encontrado");
        }

        return ResponseEntity.ok(user);
    }
}
