package com.example.notification.application.validators.notification;

import java.util.Set;
import java.util.stream.Collectors;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validator;

import org.springframework.stereotype.Component;

import com.example.notification.api.dtos.request.notification.NotificationUpdateRequest;

import io.vavr.collection.Seq;
import io.vavr.control.Either;

@Component
public class NotificationUpdateValidator {

    private final Validator validator;

    public NotificationUpdateValidator(Validator validator) {
        this.validator = validator;
    }

    public Either<Seq<String>, NotificationUpdateRequest> validate(NotificationUpdateRequest request) {
        Set<ConstraintViolation<NotificationUpdateRequest>> violations = validator.validate(request);

        if (violations.isEmpty()) {
            return Either.right(request);
        } else {
            Seq<String> errors = io.vavr.collection.List.ofAll(
                    violations.stream()
                            .map(ConstraintViolation::getMessage)
                            .collect(Collectors.toList())
            );
            return Either.left(errors);
        }
    }
}
