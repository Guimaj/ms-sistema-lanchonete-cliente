package com.fiap.mssistemalanchonetecliente.core.usecase.cliente;

import com.fiap.mssistemalanchonetecliente.core.exception.exception.ClienteAlreadyExistsException;
import com.fiap.mssistemalanchonetecliente.core.exception.exception.ClienteNotFoundException;
import com.fiap.mssistemalanchonetecliente.core.model.Cliente;
import com.fiap.mssistemalanchonetecliente.core.port.ClientePort;
import com.fiap.mssistemalanchonetecliente.core.usecase.ClienteUseCaseFacade;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.util.List;

@Service
public class ClienteUseCase implements ClienteUseCaseFacade {

    private final ClientePort clientePort;

    @Autowired
    public ClienteUseCase(ClientePort clientePort){
        this.clientePort = clientePort;
    }

    @Override
    public Cliente salvarCliente(Cliente cliente) {

        if (!ObjectUtils.isEmpty(cliente.getCpf()) && !ObjectUtils.isEmpty(clientePort.consultarClientePorCpf(cliente.getCpf())))
            throw new ClienteAlreadyExistsException(cliente.getCpf());

        cliente.setSenha(new BCryptPasswordEncoder().encode(cliente.getSenha()));

        return clientePort.salvarCliente(cliente);
    }

    public List<Cliente> consultarTodosClientes() {
        return clientePort.consultarTodosClientes();
    }

    public Cliente consultarClientePorCpf(String cpf) {
        Cliente cliente = clientePort.consultarClientePorCpf(cpf);

        if (ObjectUtils.isEmpty(cliente))
            throw new ClienteNotFoundException(cpf);

        return cliente;
    }

    public Cliente atualizarCliente(Cliente cliente, String codigo) {

        Cliente clienteAntigo = clientePort.consultarClientePorCodigo(codigo);

        if (ObjectUtils.isEmpty(clienteAntigo))
            throw new ClienteNotFoundException(String.valueOf(codigo));

        return clientePort.atualizarCliente(clienteAntigo, cliente);
    }

    public void deletarCliente(String codigo) {

        Cliente cliente = clientePort.consultarClientePorCodigo(codigo);

        if (ObjectUtils.isEmpty(cliente))
            throw new ClienteNotFoundException(String.valueOf(codigo));

        clientePort.deletarCliente(cliente);
    }
}
