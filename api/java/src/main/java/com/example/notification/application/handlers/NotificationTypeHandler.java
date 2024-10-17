package com.example.notification.application.handlers;

import com.example.notification.api.dtos.request.notification_type.NotificationTypeRequest;
import com.example.notification.api.dtos.request.notification_type.NotificationTypeUpdateRequest;

import com.example.notification.application.validators.notification_type.NotificationTypeRequestValidator;
import com.example.notification.application.validators.notification_type.NotificationTypeUpdateValidator;

import com.example.notification.domain.repositories.NotificationTypeRepository;
import com.example.notification.domain.entities.NotificationType;
import com.example.notification.domain.entities.User;

import io.vavr.collection.Seq;
import io.vavr.control.Either;

import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.List;
import java.util.UUID;

@Service
public class NotificationTypeHandler {

    private final NotificationTypeRepository notificationTypeRepository;
    private final NotificationTypeRequestValidator notificationTypeRequestValidator;
    private final NotificationTypeUpdateValidator notificationTypeUpdateValidator;

    public NotificationTypeHandler(NotificationTypeRepository notificationTypeRepository,
                                   NotificationTypeRequestValidator notificationTypeRequestValidator,
                                   NotificationTypeUpdateValidator notificationTypeUpdateValidator) {
        this.notificationTypeRepository = notificationTypeRepository;
        this.notificationTypeRequestValidator = notificationTypeRequestValidator;
        this.notificationTypeUpdateValidator = notificationTypeUpdateValidator;
    }

    //Criar um novo tipo de notificação usando Either para validação
    public Either<Seq<String>, NotificationType> createNotificationType(NotificationTypeRequest request, User user) {
        Either<Seq<String>, NotificationTypeRequest> validationResult = notificationTypeRequestValidator.validate(request);

        return validationResult.flatMap(validRequest -> {
            NotificationType notificationType = new NotificationType(validRequest.getNomeTipo(), user);
            return Either.right(notificationTypeRepository.save(notificationType));
        });
    }

    // Listar todos os tipos de notificação de um usuário
    public Either<String, List<NotificationType>> getNotificationTypesByUser(User user) {
        List<NotificationType> notificationTypes = notificationTypeRepository.findByUser(user);

        if (!notificationTypes.isEmpty()) {
            return Either.right(notificationTypes);
        } else {
            return Either.left("Nenhum tipo de notificação encontrado para o usuário.");
        }
    }

    // Atualizar um tipo de notificação usando Either para validação
    public Either<Seq<String>, NotificationType> updateNotificationType(UUID id, NotificationTypeUpdateRequest request) {
        Either<Seq<String>, NotificationTypeUpdateRequest> validationResult = notificationTypeUpdateValidator.validate(request);

        return validationResult.flatMap(validRequest -> {
            Optional<NotificationType> optionalType = notificationTypeRepository.findById(id);

            if (optionalType.isPresent()) {
                NotificationType notificationType = optionalType.get();
                notificationType.updateInfo(validRequest.getNovoNomeTipo());
                return Either.right(notificationTypeRepository.save(notificationType));
            }
            return Either.left(io.vavr.collection.List.of("Tipo de notificação não encontrado"));
        });
    }

    // Excluir um tipo de notificação
    public Either<String, Void> deleteNotificationType(UUID id) {
        Optional<NotificationType> optionalType = notificationTypeRepository.findById(id);

        if (optionalType.isPresent()) {
            notificationTypeRepository.delete(optionalType.get());
            return Either.right(null);
        } else {
            return Either.left("Tipo de notificação não encontrado");
        }
    }

    // Buscar um tipo de notificação pelo ID
    public Either<String, NotificationType> getNotificationTypeById(UUID id) {
        return notificationTypeRepository.findById(id)
                .map(Either::<String, NotificationType>right)
                .orElseGet(() -> Either.left("Tipo de notificação não encontrado"));
    }
}
