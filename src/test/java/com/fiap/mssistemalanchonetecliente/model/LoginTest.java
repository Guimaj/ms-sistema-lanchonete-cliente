package com.fiap.mssistemalanchonetecliente.model;

import com.fiap.mssistemalanchonetecliente.core.model.Login;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;


import java.util.Set;

import static org.junit.Assert.*;

@ActiveProfiles("test")
@RunWith(SpringRunner.class)
public class LoginTest {

    private Login login;
    private Validator validator;

    @Before
    public void setUp() {
        login = new Login();
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    public void testGettersAndSetters_DeveConfigurarERecuperarValoresCorretamente() {
        login.setCpf("12345678901");
        login.setSenha("senha123");

        assertEquals("12345678901", login.getCpf());
        assertEquals("senha123", login.getSenha());
    }

    @Test
    public void testCpfValido_DevePassarNaValidacao() {
        login.setCpf("123.456.789-09");
        login.setSenha("senha123");

        Set<ConstraintViolation<Login>> violations = validator.validate(login);

        assertTrue(violations.isEmpty());
    }

    @Test
    public void testCpfInvalido_DeveGerarViolacao() {
        login.setCpf("123456789");
        login.setSenha("senha123");

        Set<ConstraintViolation<Login>> violations = validator.validate(login);

        assertFalse(violations.isEmpty());
        assertEquals(1, violations.size());
        assertEquals("CPF Inválido!", violations.iterator().next().getMessage());
    }
}
