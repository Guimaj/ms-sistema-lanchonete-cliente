package com.fiap.mssistemalanchonetecliente.dataprovider.repository.mongo;

import com.fiap.mssistemalanchonetecliente.dataprovider.entity.ClienteEntity;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Repository;

@Repository
public interface IClienteRepository extends MongoRepository<ClienteEntity, String> {
  UserDetails findByCpf(String cpf);
}
