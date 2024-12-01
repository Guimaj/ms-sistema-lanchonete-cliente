package com.fiap.mssistemalanchonetecliente.core.usecase;

import com.fiap.mssistemalanchonetecliente.core.model.Login;
import com.fiap.mssistemalanchonetecliente.core.model.Token;

public interface LoginUseCaseFacade {
    Token fazerLogin(Login login);
}
