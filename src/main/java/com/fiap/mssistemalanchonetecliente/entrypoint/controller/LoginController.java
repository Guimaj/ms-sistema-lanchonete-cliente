package com.fiap.mssistemalanchonetecliente.entrypoint.controller;

import com.fiap.mssistemalanchonetecliente.core.exception.ErrorResponse;
import com.fiap.mssistemalanchonetecliente.core.model.Login;
import com.fiap.mssistemalanchonetecliente.core.model.Token;
import com.fiap.mssistemalanchonetecliente.core.usecase.LoginUseCaseFacade;
import com.fiap.mssistemalanchonetecliente.core.usecase.login.LoginUseCase;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class LoginController {

    @Autowired
    LoginUseCase loginUseCase;

    @Operation(
            description = "Realiza o login do cliente",
            responses = {
                    @ApiResponse(responseCode = "201", description = "Cliente logado com sucesso!"),
                    @ApiResponse(responseCode = "400",
                            description = "Cliente já posssui cadastro!",
                            content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
            }
    )
    @PostMapping(value = "/login", consumes = "application/json", produces = "application/json")
    public ResponseEntity<Token> login(@RequestBody @Valid final Login login) {
        return ResponseEntity.ok(loginUseCase.fazerLogin(login));
    }
}
