package com.fiap.mssistemalanchonetecliente.usecase;

import com.fiap.mssistemalanchonetecliente.core.exception.exception.ClienteAlreadyExistsException;
import com.fiap.mssistemalanchonetecliente.core.exception.exception.ClienteNotFoundException;
import com.fiap.mssistemalanchonetecliente.core.model.Cliente;
import com.fiap.mssistemalanchonetecliente.core.port.ClientePort;
import com.fiap.mssistemalanchonetecliente.core.usecase.cliente.ClienteUseCase;
import org.junit.jupiter.api.BeforeEach;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ActiveProfiles("test")
@RunWith(SpringRunner.class)
public class ClienteUseCaseTest {

    @Mock
    private ClientePort clientePort;

    @InjectMocks
    private ClienteUseCase clienteUseCase;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void salvarCliente_DeveSalvarClienteQuandoNaoExiste() {
        Cliente cliente = new Cliente();
        cliente.setCpf("12345678901");
        cliente.setSenha("senha123");

        when(clientePort.consultarClientePorCpf(cliente.getCpf())).thenReturn(null);
        when(clientePort.salvarCliente(any(Cliente.class))).thenReturn(cliente);

        Cliente resultado = clienteUseCase.salvarCliente(cliente);

        assertNotNull(resultado);
        assertNotNull(resultado.getSenha());
        verify(clientePort).salvarCliente(any(Cliente.class));
    }

    @Test
    public void salvarCliente_DeveLancarExcecaoQuandoCpfJaExiste() {
        Cliente cliente = new Cliente();
        cliente.setCpf("12345678901");

        when(clientePort.consultarClientePorCpf(cliente.getCpf())).thenReturn(cliente);

        assertThrows(ClienteAlreadyExistsException.class, () -> clienteUseCase.salvarCliente(cliente));
    }

    @Test
    public void consultarTodosClientes_DeveRetornarListaDeClientes() {
        Cliente cliente = new Cliente();
        when(clientePort.consultarTodosClientes()).thenReturn(List.of(cliente));

        List<Cliente> resultado = clienteUseCase.consultarTodosClientes();

        assertNotNull(resultado);
        assertEquals(1, resultado.size());
        assertEquals(cliente, resultado.get(0));
    }

    @Test
    public void consultarClientePorCpf_DeveRetornarClienteQuandoEncontrado() {
        String cpf = "12345678901";
        Cliente cliente = new Cliente();
        when(clientePort.consultarClientePorCpf(cpf)).thenReturn(cliente);

        Cliente resultado = clienteUseCase.consultarClientePorCpf(cpf);

        assertNotNull(resultado);
        assertEquals(cliente, resultado);
    }

    @Test
    public void consultarClientePorCpf_DeveLancarExcecaoQuandoNaoEncontrado() {
        String cpf = "12345678901";

        when(clientePort.consultarClientePorCpf(cpf)).thenReturn(null);

        assertThrows(ClienteNotFoundException.class, () -> clienteUseCase.consultarClientePorCpf(cpf));
    }

    @Test
    public void atualizarCliente_DeveAtualizarClienteQuandoEncontrado() {
        String codigo = "123";
        Cliente clienteAntigo = new Cliente();
        Cliente clienteNovo = new Cliente();
        when(clientePort.consultarClientePorCodigo(codigo)).thenReturn(clienteAntigo);
        when(clientePort.atualizarCliente(clienteAntigo, clienteNovo)).thenReturn(clienteNovo);

        Cliente resultado = clienteUseCase.atualizarCliente(clienteNovo, codigo);

        assertNotNull(resultado);
        assertEquals(clienteNovo, resultado);
    }

    @Test
    public void atualizarCliente_DeveLancarExcecaoQuandoNaoEncontrado() {
        String codigo = "123";
        Cliente clienteNovo = new Cliente();

        when(clientePort.consultarClientePorCodigo(codigo)).thenReturn(null);

        assertThrows(ClienteNotFoundException.class, () -> clienteUseCase.atualizarCliente(clienteNovo, codigo));
    }

    @Test
    public void deletarCliente_DeveDeletarClienteQuandoEncontrado() {
        String codigo = "123";
        Cliente cliente = new Cliente();
        when(clientePort.consultarClientePorCodigo(codigo)).thenReturn(cliente);
        doNothing().when(clientePort).deletarCliente(cliente);

        clienteUseCase.deletarCliente(codigo);

        verify(clientePort).deletarCliente(cliente);
    }

    @Test
    public void deletarCliente_DeveLancarExcecaoQuandoNaoEncontrado() {
        String codigo = "123";

        when(clientePort.consultarClientePorCodigo(codigo)).thenReturn(null);

        assertThrows(ClienteNotFoundException.class, () -> clienteUseCase.deletarCliente(codigo));
    }
}
