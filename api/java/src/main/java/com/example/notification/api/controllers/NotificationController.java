package com.example.notification.api.controllers;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import com.example.notification.api.dtos.request.notification.NotificationRequest;
import com.example.notification.api.dtos.request.notification.NotificationUpdateRequest;
import com.example.notification.api.dtos.response.notification.NotificationResponse;
import com.example.notification.application.handlers.NotificationHandler;
import com.example.notification.application.handlers.NotificationTypeHandler;
import com.example.notification.domain.entities.Notification;
import com.example.notification.domain.entities.NotificationType;
import com.example.notification.domain.entities.User;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.vavr.collection.Seq;
import io.vavr.control.Either;

@RestController
@CrossOrigin
@RequestMapping("/api/news")
@Tag(name = "Notificações", description = "APIs para gerenciamento de notificações")
@SecurityRequirement(name = "JSON Web Token")
public class NotificationController extends BaseController {

    private final NotificationHandler notificationHandler;
    private final NotificationTypeHandler notificationTypeHandler;

    public NotificationController(NotificationHandler notificationHandler, NotificationTypeHandler notificationTypeHandler) {
        this.notificationHandler = notificationHandler;
        this.notificationTypeHandler = notificationTypeHandler;
    }

    // Rota para criar uma nova notificação (JWT protegido)
    @Operation(summary = "Criar uma nova notificação", description = "Cria uma notificação para o usuário autenticado.")
    @PostMapping("/create")
    public ResponseEntity<?> createNotification(@RequestBody NotificationRequest request, Authentication authentication) {
        ResponseEntity<?> validationResponse = validateAuthenticatedUser(authentication);
        if (validationResponse.getStatusCode() != HttpStatus.OK) {
            return validationResponse;
        }

        User user = (User) validationResponse.getBody();

        assert user != null;
        Either<Seq<String>, Notification> result = notificationHandler.createNotification(request, user);
        return result.fold(
                errors -> ResponseEntity.badRequest().body(errors.asJava()),
                notification -> ResponseEntity.ok(mapToResponse(notification))
        );
    }

    // Rota para listar todas as notificações do usuário autenticado (JWT protegido)
    @Operation(summary = "Listar todas as notificações", description = "Lista todas as notificações do usuário autenticado.")
    @GetMapping("/me")
    public ResponseEntity<?> getNotificationsByUser(Authentication authentication) {
        ResponseEntity<?> validationResponse = validateAuthenticatedUser(authentication);
        if (validationResponse.getStatusCode() != HttpStatus.OK) {
            return validationResponse;
        }

        User user = (User) validationResponse.getBody();

        assert user != null;
        List<Notification> notifications = notificationHandler.getNotificationsByUser(user);
        List<NotificationResponse> responses = notifications.stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
        return ResponseEntity.ok(responses);
    }

    // Rota para listar todas as notificações do usuário por tipo
    @Operation(summary = "Listar notificações por tipo", description = "Lista todas as notificações do usuário autenticado por tipo.")
    @GetMapping("/type/{typeId}")
    public ResponseEntity<?> getNotificationsByUserAndType(@PathVariable UUID typeId, Authentication authentication) {
        ResponseEntity<?> validationResponse = validateAuthenticatedUser(authentication);
        if (validationResponse.getStatusCode() != HttpStatus.OK) {
            return validationResponse;
        }

        User user = (User) validationResponse.getBody();

        assert user != null;
        Either<String, NotificationType> notificationTypeEither = notificationTypeHandler.getNotificationTypeById(typeId);

        return notificationTypeEither.fold(
                error -> ResponseEntity.badRequest().body(error),
                notificationType -> {
                    List<Notification> notifications = notificationHandler.getNotificationsByUserAndType(user, notificationType);
                    List<NotificationResponse> responses = notifications.stream()
                            .map(this::mapToResponse)
                            .collect(Collectors.toList());
                    return ResponseEntity.ok(responses);
                }
        );
    }

    // Rota para atualizar uma notificação do usuário
    @Operation(summary = "Atualizar uma notificação", description = "Atualiza uma notificação específica do usuário.")
    @PutMapping("/update/{news_id}")
    public ResponseEntity<?> updateNotification(@PathVariable UUID news_id, @RequestBody NotificationUpdateRequest request) {
        Either<Seq<String>, Notification> result = notificationHandler.updateNotification(news_id, request);
        return result.fold(
                errors -> ResponseEntity.badRequest().body(errors.asJava()),
                notification -> ResponseEntity.ok(mapToResponse(notification))
        );
    }

    // Rota para deletar uma notificação do usuário
    @Operation(summary = "Deletar uma notificação", description = "Deleta uma notificação específica do usuário.")
    @DeleteMapping("/delete/{news_id}")
    public ResponseEntity<?> deleteNotification(@PathVariable UUID news_id) {
        Either<String, Void> result = notificationHandler.deleteNotification(news_id);
        return result.fold(
                error -> ResponseEntity.badRequest().body(error),
                _ -> ResponseEntity.noContent().build()
        );
    }

    // Mapear Notification para NotificationResponse
    private NotificationResponse mapToResponse(Notification notification) {
        return new NotificationResponse(
                notification.getId(),
                notification.getTitulo(),
                notification.getDescricao(),
                notification.getCorpo(),
                notification.getUser().getId(),
                notification.getNotificationType().getId(),
                notification.getImagemDestaque()
        );
    }

}

