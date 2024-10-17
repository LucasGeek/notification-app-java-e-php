package com.example.notification.infrastructure.security;

import java.util.Collection;

import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;

import com.example.notification.domain.entities.User;

public class UserAuthentication extends AbstractAuthenticationToken {

    private final String userId;
    private final User user;

    public UserAuthentication(String userId, User user, Collection<? extends GrantedAuthority> authorities) {
        super(authorities);
        this.userId = userId;
        this.user = user;
        setAuthenticated(true);
    }

    public String getUserId() {
        return userId;
    }

    public User getUser() {
        return user;
    }

    @Override
    public Object getCredentials() {
        return null; // Credenciais não são necessárias após a autenticação JWT
    }

    @Override
    public Object getPrincipal() {
        return this.userId;
    }
}
