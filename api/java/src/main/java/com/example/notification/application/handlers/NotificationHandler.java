package com.example.notification.application.handlers;

import com.example.notification.api.dtos.request.notification.NotificationRequest;
import com.example.notification.api.dtos.request.notification.NotificationUpdateRequest;
import com.example.notification.application.validators.notification.NotificationRequestValidator;
import com.example.notification.application.validators.notification.NotificationUpdateValidator;
import com.example.notification.domain.entities.Notification;
import com.example.notification.domain.entities.User;
import com.example.notification.domain.entities.NotificationType;
import com.example.notification.domain.repositories.NotificationRepository;
import com.example.notification.domain.repositories.NotificationTypeRepository;
import io.vavr.collection.Seq;
import io.vavr.control.Either;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class NotificationHandler {

    private final NotificationRepository notificationRepository;
    private final NotificationTypeRepository notificationTypeRepository;
    private final NotificationRequestValidator notificationRequestValidator;
    private final NotificationUpdateValidator notificationUpdateValidator;

    public NotificationHandler(NotificationRepository notificationRepository,
                               NotificationTypeRepository notificationTypeRepository,
                               NotificationRequestValidator notificationRequestValidator,
                               NotificationUpdateValidator notificationUpdateValidator) {
        this.notificationRequestValidator = notificationRequestValidator;
        this.notificationUpdateValidator = notificationUpdateValidator;
        this.notificationTypeRepository = notificationTypeRepository;
        this.notificationRepository = notificationRepository;
    }

    // Criar uma nova notificação usando Either para validação
    public Either<Seq<String>, Notification> createNotification(NotificationRequest request, User user) {
        Either<Seq<String>, NotificationRequest> validationResult = notificationRequestValidator.validate(request);

        return validationResult.flatMap(validRequest -> {
            Optional<NotificationType> optionalNotificationType = notificationTypeRepository.findById(request.getTypeId());

            if (optionalNotificationType.isPresent()) {
                NotificationType type = optionalNotificationType.get();
                Notification notification = new Notification(
                        validRequest.getTitulo(),
                        validRequest.getDescricao(),
                        validRequest.getCorpo(),
                        user,
                        type,
                        validRequest.getImagemDestaque()
                );
                return Either.right(notificationRepository.save(notification));
            }

            return Either.left(io.vavr.collection.List.of("Tipo de notificação não encontrada"));
        });
    }


    // Listar todas as notificações de um usuário
    public List<Notification> getNotificationsByUser(User user) {
        return notificationRepository.findByUser(user);
    }

    // Listar todas as notificações de um usuário por tipo de notificação
    public List<Notification> getNotificationsByUserAndType(User user, NotificationType type) {
        return notificationRepository.findByUserAndNotificationType(user, type);
    }

    // Atualizar uma notificação usando Either para validação
    public Either<Seq<String>, Notification> updateNotification(UUID notificationId, NotificationUpdateRequest request) {
        Either<Seq<String>, NotificationUpdateRequest> validationResult = notificationUpdateValidator.validate(request);

        return validationResult.flatMap(validRequest -> {
            Optional<Notification> optionalNotification = notificationRepository.findById(notificationId);

            if (optionalNotification.isPresent()) {
                Notification notification = optionalNotification.get();
                notification.updateInfo(
                        validRequest.getTitulo(),
                        validRequest.getDescricao(),
                        validRequest.getCorpo(),
                        validRequest.getImagemDestaque()
                );
                return Either.right(notificationRepository.save(notification));
            }

            return Either.left(io.vavr.collection.List.of("Notificação não encontrada"));
        });
    }


    // Deletar uma notificação
    public Either<String, Void> deleteNotification(UUID notificationId) {
        Optional<Notification> optionalNotification = notificationRepository.findById(notificationId);

        if (optionalNotification.isPresent()) {
            notificationRepository.delete(optionalNotification.get());
            return Either.right(null);
        } else {
            return Either.left("Notificação não encontrada");
        }
    }
}
