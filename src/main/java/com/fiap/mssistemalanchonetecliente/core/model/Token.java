package com.fiap.mssistemalanchonetecliente.core.model;

import lombok.*;


@AllArgsConstructor
@NoArgsConstructor
public class Token {
    private String jwt;

    public String getJwt() {
        return jwt;
    }

    public void setJwt(String jwt) {
        this.jwt = jwt;
    }
}
