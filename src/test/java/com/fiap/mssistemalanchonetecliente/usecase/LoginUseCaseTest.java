package com.fiap.mssistemalanchonetecliente.usecase;

import com.fiap.mssistemalanchonetecliente.core.model.Login;
import com.fiap.mssistemalanchonetecliente.core.model.Token;
import com.fiap.mssistemalanchonetecliente.core.port.TokenPort;
import com.fiap.mssistemalanchonetecliente.core.usecase.login.LoginUseCase;
import com.fiap.mssistemalanchonetecliente.dataprovider.entity.ClienteEntity;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ActiveProfiles("test")
@RunWith(SpringRunner.class)
public class LoginUseCaseTest {

    @Mock
    private AuthenticationManager authenticationManager;

    @Mock
    private TokenPort tokenPort;

    @InjectMocks
    private LoginUseCase loginUseCase;

    @Before
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void fazerLogin_DeveRetornarTokenQuandoAutenticacaoBemSucedida() {
        Login login = new Login("12345678901", "senha123");
        Authentication authentication = mock(Authentication.class);
        ClienteEntity clienteEntity = mock(ClienteEntity.class);

        String tokenGerado = "jwt-token";
        when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class))).thenReturn(authentication);
        when(authentication.getPrincipal()).thenReturn(clienteEntity);
        when(clienteEntity.getUsername()).thenReturn(login.getCpf());
        when(tokenPort.generateToken(login.getCpf())).thenReturn(tokenGerado);


        Token resultado = loginUseCase.fazerLogin(login);

        assertNotNull(resultado);
        assertEquals(tokenGerado, resultado.getJwt());
        verify(authenticationManager).authenticate(any(UsernamePasswordAuthenticationToken.class));
        verify(tokenPort).generateToken(login.getCpf());
    }

    @Test
    public void fazerLogin_DeveLancarExcecaoQuandoAutenticacaoFalha() {
        Login login = new Login("12345678901", "senhaIncorreta");

        when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class)))
                .thenThrow(new RuntimeException("Falha na autenticação"));

        assertThrows(RuntimeException.class, () -> loginUseCase.fazerLogin(login));
        verify(authenticationManager).authenticate(any(UsernamePasswordAuthenticationToken.class));
        verifyNoInteractions(tokenPort);
    }
}
