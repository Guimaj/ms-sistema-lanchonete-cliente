package com.fiap.mssistemalanchonetecliente.dataprovider.repository;

import com.fiap.mssistemalanchonetecliente.core.model.Cliente;
import com.fiap.mssistemalanchonetecliente.core.port.ClientePort;
import com.fiap.mssistemalanchonetecliente.dataprovider.entity.ClienteEntity;
import com.fiap.mssistemalanchonetecliente.dataprovider.mapper.ClienteMapper;
import com.fiap.mssistemalanchonetecliente.dataprovider.repository.mongo.IClienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Repository
public class ClienteRepository implements ClientePort {

    private final IClienteRepository iClienteRepository;
    private final ClienteMapper clienteMapper;

    @Autowired
    public ClienteRepository(IClienteRepository iClienteRepository, ClienteMapper clienteMapper){
        this.iClienteRepository = iClienteRepository;
        this.clienteMapper = clienteMapper;
    }

    @Override
    @Transactional
    public Cliente salvarCliente(Cliente cliente) {
        return clienteMapper.toDomain(iClienteRepository.save(clienteMapper.toEntity(cliente)));
    }

    @Override
    public List<Cliente> consultarTodosClientes() {
        return iClienteRepository.findAll()
                .stream()
                .map(clienteMapper::toDomain)
                .toList();
    }

    @Override
    public Cliente consultarClientePorCpf(String cpf) {
        return Optional.ofNullable(iClienteRepository.findByCpf(cpf))
                .map(ClienteEntity.class::cast)
                .map(clienteMapper::toDomain)
                .orElse(null);
    }

    @Override
    public Cliente consultarClientePorCodigo(String codigo) {
        return iClienteRepository.findById(codigo)
                .map(clienteMapper::toDomain)
                .orElse(null);
    }

    @Override
    public Cliente atualizarCliente(Cliente clienteAntigo, Cliente clienteNovo) {
        ClienteEntity clienteEntity = clienteMapper.toEntity(clienteAntigo);
        clienteMapper.merge(clienteNovo, clienteEntity);
        return clienteMapper.toDomain(iClienteRepository.save(clienteEntity));
    }

    @Override
    public void deletarCliente(Cliente cliente) {
        iClienteRepository.delete(clienteMapper.toEntity(cliente));
    }
}
