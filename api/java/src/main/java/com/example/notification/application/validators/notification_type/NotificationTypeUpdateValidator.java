package com.example.notification.application.validators.notification_type;

import java.util.Set;
import java.util.stream.Collectors;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validator;

import org.springframework.stereotype.Component;

import com.example.notification.api.dtos.request.notification_type.NotificationTypeUpdateRequest;

import io.vavr.collection.Seq;
import io.vavr.control.Either;

@Component
public class NotificationTypeUpdateValidator {

    private final Validator validator;

    public NotificationTypeUpdateValidator(Validator validator) {
        this.validator = validator;
    }

    public Either<Seq<String>, NotificationTypeUpdateRequest> validate(NotificationTypeUpdateRequest request) {
        Set<ConstraintViolation<NotificationTypeUpdateRequest>> violations = validator.validate(request);

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
