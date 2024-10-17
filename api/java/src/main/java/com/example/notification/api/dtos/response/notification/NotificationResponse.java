package com.example.notification.api.dtos.response.notification;

import java.util.UUID;

public class NotificationResponse {

    private UUID id;
    private String titulo;
    private String descricao;
    private String corpo;
    private UUID userId;
    private UUID typeId;
    private String imagemDestaque;

    public NotificationResponse() { }

    public NotificationResponse(UUID id, String titulo, String descricao, String corpo, UUID userId, UUID typeId, String imagemDestaque) {
        this.id = id;
        this.titulo = titulo;
        this.descricao = descricao;
        this.corpo = corpo;
        this.userId = userId;
        this.typeId = typeId;
        this.imagemDestaque = imagemDestaque;
    }

    // Getters e Setters
    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

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

    public UUID getUserId() {
        return userId;
    }

    public void setUserId(UUID userId) {
        this.userId = userId;
    }

    public UUID getTypeId() {
        return typeId;
    }

    public void setTypeId(UUID typeId) {
        this.typeId = typeId;
    }

    public String getImagemDestaque() {
        return imagemDestaque;
    }

    public void setImagemDestaque(String imagemDestaque) {
        this.imagemDestaque = imagemDestaque;
    }
}
