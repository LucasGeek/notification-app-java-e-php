package com.example.notification.api.dtos.request.notification;

import java.util.UUID;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

public class NotificationRequest {

    @NotNull(message = "O ID do tipo de notificação é obrigatório")
    private UUID typeId;

    @NotBlank(message = "O título é obrigatório")
    private String titulo;

    @NotBlank(message = "A descrição é obrigatória")
    private String descricao;

    @NotBlank(message = "O corpo da notificação é obrigatório")
    private String corpo;

    @Pattern(
            regexp = "^(http://|https://)?(www\\.)?[a-zA-Z0-9-]+(\\.[a-zA-Z]+)+(/.*)?$",
            message = "A URL da imagem de destaque não é válida"
    )
    private String imagemDestaque;

    public NotificationRequest() { }

    public NotificationRequest(UUID typeId, String titulo, String descricao, String corpo, String imagemDestaque) {
        this.typeId = typeId;
        this.titulo = titulo;
        this.descricao = descricao;
        this.corpo = corpo;
        this.imagemDestaque = imagemDestaque;
    }

    // Getters e Setters
    public UUID getTypeId() {
        return typeId;
    }

    public String getTitulo() {
        return titulo;
    }

    public String getDescricao() {
        return descricao;
    }

    public String getCorpo() {
        return corpo;
    }

    public String getImagemDestaque() {
        return imagemDestaque;
    }
}
