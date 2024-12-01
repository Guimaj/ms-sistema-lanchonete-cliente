package com.fiap.mssistemalanchonetecliente.core.model;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import lombok.*;
import org.hibernate.validator.constraints.br.CPF;


@AllArgsConstructor
@NoArgsConstructor
public class Cliente {
    private String codigo;
    @CPF(message = "CPF Inválido!")
    private String cpf;
    private String nome;
    @Email(message = "Email inválido!")
    private String email;
    @NotEmpty
    private String senha;

    public Cliente(String codigo,
                   String cpf,
                   String senha) {
        this.codigo = codigo;
        this.cpf = cpf;
        this.senha = senha;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }
}
