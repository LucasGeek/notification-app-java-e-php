package com.example.notification.api.controllers;

import java.util.List;
import java.util.UUID;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import com.example.notification.api.dtos.request.notification_type.NotificationTypeRequest;
import com.example.notification.api.dtos.request.notification_type.NotificationTypeUpdateRequest;
import com.example.notification.api.dtos.response.notification_type.NotificationTypeResponse;
import com.example.notification.application.handlers.NotificationTypeHandler;
import com.example.notification.domain.entities.NotificationType;
import com.example.notification.domain.entities.User;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.vavr.collection.Seq;
import io.vavr.control.Either;

@RestController
@CrossOrigin
@RequestMapping("/api/type")
@Tag(name = "Tipos de Notificações", description = "APIs para gerenciamento de tipos de notificação")
@SecurityRequirement(name = "JSON Web Token")
public class NotificationTypeController extends BaseController {

    private final NotificationTypeHandler notificationTypeHandler;

    public NotificationTypeController(NotificationTypeHandler notificationTypeHandler) {
        this.notificationTypeHandler = notificationTypeHandler;
    }

    // Rota para criar um novo tipo de notificação
    @Operation(summary = "Criar um novo tipo de notificação", description = "Cria um novo tipo de notificação para o usuário autenticado.")
    @PostMapping("/create")
    public ResponseEntity<?> createNotificationType(@RequestBody NotificationTypeRequest request, Authentication authentication) {
        ResponseEntity<?> validationResponse = validateAuthenticatedUser(authentication);
        if (validationResponse.getStatusCode() != HttpStatus.OK) {
            return validationResponse;
        }

        User user = (User) validationResponse.getBody();

        assert user != null;
        Either<Seq<String>, NotificationType> result = notificationTypeHandler.createNotificationType(request, user);
        return result.fold(
                errors -> ResponseEntity.badRequest().body(errors.asJava()),
                notificationType -> ResponseEntity.ok(mapToResponse(notificationType))
        );
    }

    // Listar todos os tipos de notificação do usuário autenticado
    @Operation(summary = "Listar tipos de notificação do usuário", description = "Lista todos os tipos de notificação do usuário autenticado.")
    @GetMapping("/me")
    public ResponseEntity<?> getNotificationTypesByUser(Authentication authentication) {
        ResponseEntity<?> validationResponse = validateAuthenticatedUser(authentication);
        if (validationResponse.getStatusCode() != HttpStatus.OK) {
            return validationResponse;
        }

        User user = (User) validationResponse.getBody();

        assert user != null;
        Either<String, List<NotificationType>> result = notificationTypeHandler.getNotificationTypesByUser(user);

        return result.fold(
                error -> ResponseEntity.badRequest().body(error),
                notificationTypes -> {
                    List<NotificationTypeResponse> responses = notificationTypes.stream()
                            .map(this::mapToResponse)
                            .toList();
                    return ResponseEntity.ok(responses);
                }
        );
    }

    // Rota para atualizar um tipo de notificação
    @Operation(summary = "Atualizar um tipo de notificação", description = "Atualiza um tipo de notificação para o usuário autenticado.")
    @PutMapping("/update/{type_id}")
    public ResponseEntity<?> updateNotificationType(@PathVariable UUID type_id, @RequestBody NotificationTypeUpdateRequest request) {
        Either<Seq<String>, NotificationType> result = notificationTypeHandler.updateNotificationType(type_id, request);
        return result.fold(
                errors -> ResponseEntity.badRequest().body(errors.asJava()),
                notificationType -> ResponseEntity.ok(mapToResponse(notificationType))
        );
    }

    // Rota para excluir um tipo de notificação
    @Operation(summary = "Deletar um tipo de notificação", description = "Exclui um tipo de notificação para o usuário autenticado.")
    @DeleteMapping("/delete/{type_id}")
    public ResponseEntity<?> deleteNotificationType(@PathVariable UUID type_id) {
        Either<String, Void> result = notificationTypeHandler.deleteNotificationType(type_id);
        return result.fold(
                error -> ResponseEntity.badRequest().body(error),
                _ -> ResponseEntity.noContent().build()
        );
    }

    // Auxiliar para mapear NotificationType para NotificationTypeResponse
    private NotificationTypeResponse mapToResponse(NotificationType notificationType) {
        return new NotificationTypeResponse(
                notificationType.getId(),
                notificationType.getNomeTipo(),
                notificationType.getUser().getId()
        );
    }
}