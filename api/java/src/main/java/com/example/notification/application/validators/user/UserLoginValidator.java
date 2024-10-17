package com.example.notification.application.validators.user;

import java.util.Set;
import java.util.stream.Collectors;

import com.example.notification.api.dtos.request.user.UserLoginRequest;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validator;

import org.springframework.stereotype.Component;

import io.vavr.collection.List;
import io.vavr.collection.Seq;
import io.vavr.control.Either;

@Component
public class UserLoginValidator {

    private final Validator validator;

    public UserLoginValidator(Validator validator) {
        this.validator = validator;
    }

    public Either<Seq<String>, UserLoginRequest> validate(UserLoginRequest request) {
        Set<ConstraintViolation<UserLoginRequest>> violations = validator.validate(request);

        if (!violations.isEmpty()) {
            Seq<String> errors = List.ofAll(
                    violations.stream()
                            .map(ConstraintViolation::getMessage)
                            .collect(Collectors.toList())
            );
            return Either.left(errors);
        }

        return Either.right(request);
    }
}