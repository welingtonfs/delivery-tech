package com.deliverytech.delivery_api.model;

import org.springframework.security.core.GrantedAuthority;

public enum Role implements GrantedAuthority {
    
    ADMIN, CLIENT, RESTAURANTE, ENTREGADOR, USER;

    @Override
    public String getAuthority() {
        return name();
    }
}
