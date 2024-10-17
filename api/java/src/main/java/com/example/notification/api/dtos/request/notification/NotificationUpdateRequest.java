package com.example.notification.api.dtos.request.notification;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

public class NotificationUpdateRequest {

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

    public NotificationUpdateRequest() { }

    public NotificationUpdateRequest(String titulo, String descricao, String corpo, String imagemDestaque) {
        this.titulo = titulo;
        this.descricao = descricao;
        this.corpo = corpo;
        this.imagemDestaque = imagemDestaque;
    }

    // Getters e Setters
    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public String getCorpo() {
        return corpo;
    }

    public void setCorpo(String corpo) {
        this.corpo = corpo;
    }

    public String getImagemDestaque() {
        return imagemDestaque;
    }

    public void setImagemDestaque(String imagemDestaque) {
        this.imagemDestaque = imagemDestaque;
    }
}
