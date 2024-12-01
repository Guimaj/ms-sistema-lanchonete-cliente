package com.fiap.mssistemalanchonetecliente.repository;


import com.fiap.mssistemalanchonetecliente.core.model.Cliente;
import com.fiap.mssistemalanchonetecliente.dataprovider.entity.ClienteEntity;
import com.fiap.mssistemalanchonetecliente.dataprovider.mapper.ClienteMapper;
import com.fiap.mssistemalanchonetecliente.dataprovider.repository.ClienteRepository;
import com.fiap.mssistemalanchonetecliente.dataprovider.repository.mongo.IClienteRepository;
import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ActiveProfiles("test")
@RunWith(SpringRunner.class)
public class ClienteRepositoryTest {

    @Mock
    private IClienteRepository iClienteRepository;

    @Mock
    private ClienteMapper clienteMapper;

    @InjectMocks
    private ClienteRepository clienteRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void salvarCliente_DeveSalvarClienteERetornar() {
        Cliente cliente = new Cliente();
        ClienteEntity clienteEntity = new ClienteEntity();
        when(clienteMapper.toEntity(cliente)).thenReturn(clienteEntity);
        when(iClienteRepository.save(clienteEntity)).thenReturn(clienteEntity);
        when(clienteMapper.toDomain(clienteEntity)).thenReturn(cliente);

        Cliente resultado = clienteRepository.salvarCliente(cliente);

        assertNotNull(resultado);
        assertEquals(cliente, resultado);
        verify(iClienteRepository).save(clienteEntity);
    }

    @Test
    public void consultarTodosClientes_DeveRetornarListaDeClientes() {
        ClienteEntity clienteEntity = new ClienteEntity();
        Cliente cliente = new Cliente();
        when(iClienteRepository.findAll()).thenReturn(List.of(clienteEntity));
        when(clienteMapper.toDomain(clienteEntity)).thenReturn(cliente);

        List<Cliente> resultado = clienteRepository.consultarTodosClientes();

        assertNotNull(resultado);
        assertEquals(1, resultado.size());
        assertEquals(cliente, resultado.get(0));
    }

    @Test
    public void consultarClientePorCpf_DeveRetornarClienteQuandoEncontrado() {
        String cpf = "12345678901";
        ClienteEntity clienteEntity = new ClienteEntity();
        Cliente cliente = new Cliente();
        when(iClienteRepository.findByCpf(cpf)).thenReturn(clienteEntity);
        when(clienteMapper.toDomain(clienteEntity)).thenReturn(cliente);

        Cliente resultado = clienteRepository.consultarClientePorCpf(cpf);

        assertNotNull(resultado);
        assertEquals(cliente, resultado);
    }

    @Test
    public void consultarClientePorCpf_DeveRetornarNullQuandoNaoEncontrado() {
        String cpf = "12345678901";
        when(iClienteRepository.findByCpf(cpf)).thenReturn(null);

        Cliente resultado = clienteRepository.consultarClientePorCpf(cpf);

        assertNull(resultado);
    }

    @Test
    public void consultarClientePorCodigo_DeveRetornarClienteQuandoEncontrado() {
        String codigo = "123";
        ClienteEntity clienteEntity = new ClienteEntity();
        Cliente cliente = new Cliente();
        when(iClienteRepository.findById(codigo)).thenReturn(Optional.of(clienteEntity));
        when(clienteMapper.toDomain(clienteEntity)).thenReturn(cliente);

        Cliente resultado = clienteRepository.consultarClientePorCodigo(codigo);

        assertNotNull(resultado);
        assertEquals(cliente, resultado);
    }

    @Test
    public void consultarClientePorCodigo_DeveRetornarNullQuandoNaoEncontrado() {
        String codigo = "123";
        when(iClienteRepository.findById(codigo)).thenReturn(Optional.empty());

        Cliente resultado = clienteRepository.consultarClientePorCodigo(codigo);

        assertNull(resultado);
    }

    @Test
    public void atualizarCliente_DeveAtualizarETransformarCliente() {
        Cliente clienteAntigo = new Cliente();
        Cliente clienteNovo = new Cliente();
        ClienteEntity clienteEntity = new ClienteEntity();
        when(clienteMapper.toEntity(clienteAntigo)).thenReturn(clienteEntity);
        doNothing().when(clienteMapper).merge(clienteNovo, clienteEntity);
        when(iClienteRepository.save(clienteEntity)).thenReturn(clienteEntity);
        when(clienteMapper.toDomain(clienteEntity)).thenReturn(clienteNovo);

        Cliente resultado = clienteRepository.atualizarCliente(clienteAntigo, clienteNovo);

        assertNotNull(resultado);
        assertEquals(clienteNovo, resultado);
        verify(iClienteRepository).save(clienteEntity);
    }

    @Test
    public void deletarCliente_DeveExcluirCliente() {
        Cliente cliente = new Cliente();
        ClienteEntity clienteEntity = new ClienteEntity();
        when(clienteMapper.toEntity(cliente)).thenReturn(clienteEntity);
        doNothing().when(iClienteRepository).delete(clienteEntity);

        clienteRepository.deletarCliente(cliente);

        verify(iClienteRepository).delete(clienteEntity);
    }
}
