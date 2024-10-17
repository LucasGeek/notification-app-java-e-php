package com.example.notification.api.dtos.request.notification_type;

import jakarta.validation.constraints.NotBlank;

public class NotificationTypeUpdateRequest {

    @NotBlank(message = "O novo nome do tipo de notificação é obrigatório")
    private String novoNomeTipo;

    public NotificationTypeUpdateRequest() { }

    public NotificationTypeUpdateRequest(String novoNomeTipo) {
        this.novoNomeTipo = novoNomeTipo;
    }

    // Getter e Setter
    public String getNovoNomeTipo() {
        return novoNomeTipo;
    }
}
