package com.example.notification.application.handlers;

import java.util.ArrayList;
import java.util.Optional;
import java.util.UUID;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import com.example.notification.api.dtos.request.user.UserLoginRequest;
import com.example.notification.api.dtos.request.user.UserRegisterRequest;
import com.example.notification.application.validators.user.UserLoginValidator;
import com.example.notification.application.validators.user.UserRegisterValidator;
import com.example.notification.domain.entities.User;
import com.example.notification.domain.repositories.UserRepository;
import com.example.notification.infrastructure.security.JwtTokenProvider;
import com.example.notification.infrastructure.security.UserAuthentication;

import io.vavr.collection.List;
import io.vavr.collection.Seq;
import io.vavr.control.Either;

@Component
public class UserHandler {

    private final UserRegisterValidator userRegisterValidator;
    private final UserLoginValidator userLoginValidator;
    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private final JwtTokenProvider jwtTokenProvider;

    public UserHandler(
            JwtTokenProvider jwtTokenProvider,
            UserRepository userRepository,
            PasswordEncoder passwordEncoder,
            UserRegisterValidator userRegisterValidator,
            UserLoginValidator userLoginValidator) {
        this.userRegisterValidator = userRegisterValidator;
        this.userLoginValidator = userLoginValidator;
        this.passwordEncoder = passwordEncoder;
        this.userRepository = userRepository;
        this.jwtTokenProvider = jwtTokenProvider;
    }

    public Either<Seq<String>, User> registerUser(UserRegisterRequest request) {
        return userRegisterValidator.validate(request)
                .flatMap(validRequest -> {
                    Optional<User> userOptional = userRepository.findByEmail(validRequest.getEmail());
                    if (userOptional.isPresent()) {
                        return Either.left(List.of("Email já cadastrado"));
                    }

                    String senhaCriptografada = passwordEncoder.encode(validRequest.getSenha());
                    User newUser = new User(validRequest.getNome(), validRequest.getSobrenome(), validRequest.getEmail(), senhaCriptografada);
                    User savedUser = userRepository.save(newUser);
                    return Either.right(savedUser);
                });
    }

    public Either<String, String> loginUser(UserLoginRequest request) {
        Either<Seq<String>, UserLoginRequest> validationResult = userLoginValidator.validate(request);

        return validationResult
                .mapLeft(errors -> errors.mkString(", "))
                .flatMap(validRequest -> {
                    Optional<User> userOptional = userRepository.findByEmail(validRequest.getEmail());
                    if (userOptional.isEmpty()) {
                        return Either.left("Usuário não encontrado ou senha inválida");
                    }

                    User user = userOptional.get();

                    if (!passwordEncoder.matches(validRequest.getSenha(), user.getSenha())) {
                        return Either.left("Usuário não encontrado ou senha inválida");
                    }

                    String token = jwtTokenProvider.createToken(new UserAuthentication(user.getId().toString(), user, new ArrayList<>()));

                    return Either.right(token);
                });
    }
}
