package ru.ivanov.ecommerceplatformproject.productservice.security;

import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;
import java.util.UUID;

public class JwtPrincipal implements UserDetails {
    @Getter
    private final UUID id;
    private final String username;
    private final List<GrantedAuthority> roles;

    public JwtPrincipal(UUID id, String username, List<GrantedAuthority> roles) {
        this.id = id;
        this.username = username;
        this.roles = roles;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return roles;
    }

    @Override
    public String getPassword() {
        return null;
    }

    @Override
    public String getUsername() {
        return username;
    }
}
