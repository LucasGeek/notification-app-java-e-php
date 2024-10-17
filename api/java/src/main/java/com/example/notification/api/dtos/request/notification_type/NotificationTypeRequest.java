package com.example.notification.api.dtos.request.notification_type;

import jakarta.validation.constraints.NotBlank;

public class NotificationTypeRequest {

    @NotBlank(message = "O nome do tipo de notificação é obrigatório")
    private String nomeTipo;

    public NotificationTypeRequest() { }

    public NotificationTypeRequest(String nomeTipo) {
        this.nomeTipo = nomeTipo;
    }

    // Getter e Setter
    public String getNomeTipo() {
        return nomeTipo;
    }
}
