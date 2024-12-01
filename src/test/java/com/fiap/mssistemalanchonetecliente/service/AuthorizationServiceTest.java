package com.fiap.mssistemalanchonetecliente.service;

import com.fiap.mssistemalanchonetecliente.dataprovider.repository.mongo.IClienteRepository;
import com.fiap.mssistemalanchonetecliente.dataprovider.entity.ClienteEntity;
import com.fiap.mssistemalanchonetecliente.dataprovider.service.AuthorizationService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ActiveProfiles("test")
@RunWith(SpringRunner.class)
public class AuthorizationServiceTest {

    @Mock
    private IClienteRepository clienteRepository;

    @InjectMocks
    private AuthorizationService authorizationService;

    @Before
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void loadUserByUsername_DeveRetornarUserDetailsQuandoCpfExistir() {
        String cpf = "12345678901";
        ClienteEntity clienteEntity = new ClienteEntity();
        when(clienteRepository.findByCpf(cpf)).thenReturn(clienteEntity);

        UserDetails userDetails = authorizationService.loadUserByUsername(cpf);

        assertNotNull(userDetails);
        assertEquals(clienteEntity, userDetails);
        verify(clienteRepository).findByCpf(cpf);
    }
}
