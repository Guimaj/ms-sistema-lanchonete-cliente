package com.fiap.mssistemalanchonetecliente.core.model;

import lombok.*;

@AllArgsConstructor
public class Token {
    private String jwt;

    public String getJwt() {
        return jwt;
    }
}
