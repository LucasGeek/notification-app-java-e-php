package com.example.notification.api.dtos.response.notification_type;

import java.util.UUID;

public class NotificationTypeResponse {

    private UUID id;
    private String nomeTipo;
    private UUID userId;

    public NotificationTypeResponse() { }

    public NotificationTypeResponse(UUID id, String nomeTipo, UUID userId) {
        this.id = id;
        this.nomeTipo = nomeTipo;
        this.userId = userId;
    }

    // Getters e Setters
    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getNomeTipo() {
        return nomeTipo;
    }

    public void setNomeTipo(String nomeTipo) {
        this.nomeTipo = nomeTipo;
    }

    public UUID getUserId() {
        return userId;
    }

    public void setUserId(UUID userId) {
        this.userId = userId;
    }
}
