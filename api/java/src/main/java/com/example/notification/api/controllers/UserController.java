package com.example.notification.api.controllers;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import com.example.notification.api.dtos.request.user.UserLoginRequest;
import com.example.notification.api.dtos.request.user.UserRegisterRequest;
import com.example.notification.api.dtos.response.user.UserResponse;
import com.example.notification.application.handlers.UserHandler;
import com.example.notification.domain.entities.User;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.vavr.collection.Seq;
import io.vavr.control.Either;

@RestController
@CrossOrigin
@RequestMapping("/api")
@Tag(name = "Usuários", description = "APIs para gerenciamento de usuários")
public class UserController extends BaseController {

    private final UserHandler userHandler;

    public UserController(UserHandler userHandler) {
        this.userHandler = userHandler;
    }

    // Rota para registrar um novo usuário
    @Operation(summary = "Registrar um novo usuário", description = "Cria um novo usuário com os dados fornecidos.")
    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@RequestBody UserRegisterRequest request) {
        Either<Seq<String>, User> result = userHandler.registerUser(request);
        return result.fold(
                errors -> ResponseEntity.badRequest().body(errors.asJava()),
                user -> ResponseEntity.ok(mapToResponse(user))
        );
    }

    // Rota para login do usuário (autenticação)
    @Operation(summary = "Login do usuário", description = "Autentica o usuário e retorna um token JWT.")
    @PostMapping("/login")
    public ResponseEntity<?> loginUser(@RequestBody UserLoginRequest request) {
        Either<String, String> result = userHandler.loginUser(request); // Sucesso retorna o JWT token
        return result.fold(
                error -> ResponseEntity.badRequest().body(error),
                ResponseEntity::ok
        );
    }

    // Rota para obter os dados do usuário autenticado (JWT necessário)
    @Operation(summary = "Obter dados do usuário autenticado", description = "Retorna os dados do usuário autenticado.",
            security = { @SecurityRequirement(name = "JSON Web Token") })
    @PostMapping("/me")
    public ResponseEntity<?> getUserProfile(Authentication authentication) {
        ResponseEntity<?> validationResponse = validateAuthenticatedUser(authentication);
        if (validationResponse.getStatusCode() != HttpStatus.OK) {
            return validationResponse;
        }

        User user = (User) validationResponse.getBody();

        assert user != null;
        return ResponseEntity.ok(mapToResponse(user));
    }

    // Auxiliar para mapear User para UserResponse
    private UserResponse mapToResponse(User user) {
        return new UserResponse(
                user.getId(),
                user.getNome(),
                user.getSobrenome(),
                user.getEmail()
        );
    }
}

