package com.fiap.mssistemalanchonetecliente.service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.fiap.mssistemalanchonetecliente.dataprovider.service.TokenService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.util.ReflectionTestUtils;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

@ActiveProfiles("test")
@RunWith(SpringRunner.class)
public class TokenServiceTest {

    @InjectMocks
    private TokenService tokenService;

    @Before
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        ReflectionTestUtils.setField(tokenService, "tokenSecret", "my-secret-key");
    }

    @Test
    public void generateToken_DeveRetornarTokenQuandoLoginValido() {
        String login = "user123";
        String token = tokenService.generateToken(login);

        assertNotNull(token);
        assertTrue(token.startsWith("eyJ"));
        JWT.require(Algorithm.HMAC256("my-secret-key"))
                .withIssuer("auth-api")
                .build()
                .verify(token);
    }
}
