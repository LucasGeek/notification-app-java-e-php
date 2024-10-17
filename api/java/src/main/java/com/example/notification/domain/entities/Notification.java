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
@Table(name = "notifications")
public class Notification {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @NotBlank(message = "O título é obrigatório")
    @Column(nullable = false)
    private String titulo;

    @NotBlank(message = "A descrição é obrigatória")
    @Column(nullable = false)
    private String descricao;

    @NotBlank(message = "O corpo da notificação é obrigatório")
    @Column(nullable = false)
    private String corpo;

    @Column(nullable = true)
    private String imagemDestaque;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne
    @JoinColumn(name = "type_id", nullable = false)
    private NotificationType notificationType;

    public Notification() { }

    public Notification(String titulo, String descricao, String corpo, User user, NotificationType notificationType, String imagemDestaque) {
        this.titulo = titulo;
        this.descricao = descricao;
        this.corpo = corpo;
        this.user = user;
        this.notificationType = notificationType;
        this.imagemDestaque = imagemDestaque;
    }

    // Getters
    public UUID getId() {
        return id;
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

    public User getUser() {
        return user;
    }

    public NotificationType getNotificationType() {
        return notificationType;
    }

    // Atualização segura de dados, incluindo imagemDestaque
    public void updateInfo(String titulo, String descricao, String corpo, String imagemDestaque) {
        if (titulo != null && !titulo.trim().isEmpty()) {
            this.titulo = titulo;
        }
        if (descricao != null && !descricao.trim().isEmpty()) {
            this.descricao = descricao;
        }
        if (corpo != null && !corpo.trim().isEmpty()) {
            this.corpo = corpo;
        }
        if (imagemDestaque != null && !imagemDestaque.trim().isEmpty()) {
            this.imagemDestaque = imagemDestaque;
        }
    }
}
