package com.example.notification.domain.repositories;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.notification.domain.entities.Notification;
import com.example.notification.domain.entities.NotificationType;
import com.example.notification.domain.entities.User;

public interface NotificationRepository extends JpaRepository<Notification, UUID> {

    // Encontrar todas as notificações de um usuário
    List<Notification> findByUser(User user);

    // Encontrar todas as notificações por tipo de notificação
    List<Notification> findByNotificationType(NotificationType notificationType);

    // Encontrar todas as notificações de um usuário por tipo de notificação
    List<Notification> findByUserAndNotificationType(User user, NotificationType notificationType);
}
