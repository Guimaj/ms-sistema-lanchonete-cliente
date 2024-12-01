package com.fiap.mssistemalanchonetecliente.core.model;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import lombok.*;
import org.hibernate.validator.constraints.br.CPF;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
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
}
