package com.example.notification.domain.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.notification.domain.entities.NotificationType;
import com.example.notification.domain.entities.User;

import java.util.List;
import java.util.UUID;

public interface NotificationTypeRepository extends JpaRepository<NotificationType, UUID> {

    // Encontrar todos os tipos de notificação de um usuário
    List<NotificationType> findByUser(User user);
}
