package com.fiap.mssistemalanchonetecliente.core.usecase;

import com.fiap.mssistemalanchonetecliente.core.model.Cliente;

import java.util.List;

public interface ClienteUseCaseFacade {

    Cliente salvarCliente(Cliente cliente);

    Cliente atualizarCliente(Cliente cliente, String codigo);

    void deletarCliente(String codigo);

    Cliente consultarClientePorCpf(String cpf);

    List<Cliente> consultarTodosClientes();

}
