package com.fiap.mssistemalanchonetecliente.repository;

import com.fiap.mssistemalanchonetecliente.dataprovider.entity.ClienteEntity;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Collection;

import static org.junit.Assert.*;

@ActiveProfiles("test")
@RunWith(SpringRunner.class)
public class ClienteEntityTest {

    private ClienteEntity clienteEntity;

    @Before
    public void setUp() {
        clienteEntity = new ClienteEntity();
        clienteEntity.setCodigo("123");
        clienteEntity.setCpf("12345678901");
        clienteEntity.setNome("João Silva");
        clienteEntity.setEmail("joao.silva@example.com");
        clienteEntity.setSenha("senha123");
    }

    @Test
    public void testGetAuthorities_DeveRetornarRoleUser() {
        Collection<? extends GrantedAuthority> authorities = clienteEntity.getAuthorities();

        assertNotNull(authorities);
        assertEquals(1, authorities.size());
        assertEquals("ROLE_USER", authorities.iterator().next().getAuthority());
    }

    @Test
    public void testGetPassword_DeveRetornarSenha() {
        assertEquals("senha123", clienteEntity.getPassword());
    }

    @Test
    public void testGetUsername_DeveRetornarCpf() {
        assertEquals("12345678901", clienteEntity.getUsername());
    }

    @Test
    public void testIsAccountNonExpired_DeveRetornarTrue() {
        assertTrue(clienteEntity.isAccountNonExpired());
    }

    @Test
    public void testIsAccountNonLocked_DeveRetornarTrue() {
        assertTrue(clienteEntity.isAccountNonLocked());
    }

    @Test
    public void testIsCredentialsNonExpired_DeveRetornarTrue() {
        assertTrue(clienteEntity.isCredentialsNonExpired());
    }

    @Test
    public void testIsEnabled_DeveRetornarTrue() {
        assertTrue(clienteEntity.isEnabled());
    }

    @Test
    public void testGettersAndSetters_DeveConfigurarERecuperarValoresCorretamente() {
        clienteEntity.setCodigo("456");
        clienteEntity.setCpf("09876543210");
        clienteEntity.setNome("Maria Oliveira");
        clienteEntity.setEmail("maria.oliveira@example.com");
        clienteEntity.setSenha("novaSenha");

        assertEquals("456", clienteEntity.getCodigo());
        assertEquals("09876543210", clienteEntity.getCpf());
        assertEquals("Maria Oliveira", clienteEntity.getNome());
        assertEquals("maria.oliveira@example.com", clienteEntity.getEmail());
        assertEquals("novaSenha", clienteEntity.getSenha());
    }
}
