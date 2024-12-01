package com.fiap.mssistemalanchonetecliente.core.port;

import com.fiap.mssistemalanchonetecliente.core.model.Cliente;

import java.util.List;

public interface ClientePort {
    Cliente salvarCliente(Cliente cliente);
    List<Cliente> consultarTodosClientes();
    Cliente consultarClientePorCpf(String cpf);
    Cliente consultarClientePorCodigo(String codigo);
    Cliente atualizarCliente(Cliente clienteAntigo, Cliente clienteNovo);
    void deletarCliente(Cliente cliente);
}
