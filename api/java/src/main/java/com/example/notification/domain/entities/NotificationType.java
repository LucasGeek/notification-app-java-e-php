package com.example.notification.domain.entities;

import java.util.UUID;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;

@Entity
@Table(name = "notification_types")
public class NotificationType {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @NotBlank(message = "Nome do tipo é obrigatório")
    @Column(nullable = false)
    private String nomeTipo;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    public NotificationType() { }

    public NotificationType(String nomeTipo, User user) {
        this.nomeTipo = nomeTipo;
        this.user = user;
    }

    // Getters
    public UUID getId() {
        return id;
    }

    public String getNomeTipo() {
        return nomeTipo;
    }

    public User getUser() {
        return user;
    }

    // Atualização segura de dados
    public void updateInfo(String nomeTipo) {
        if (nomeTipo != null && !nomeTipo.trim().isEmpty()) {
            this.nomeTipo = nomeTipo;
        }
    }
}
