Feature: Gerenciamento de Clientes
  Como um usuário da API
  Quero gerenciar os clientes
  Para cadastrar, consultar, atualizar e remover clientes

  # Cenário 1: Consultar todos os clientes
  Scenario: Consultar todos os clientes
    Given que existem clientes cadastrados
    When eu consultar todos os clientes
    Then eu recebo o status HTTP 200
    And a lista de clientes não é vazia

  # Cenário 2: Consultar cliente por CPF
  Scenario: Consultar cliente por CPF
    Given que existe um cliente cadastrado com CPF "<cpf>"
    When eu consultar o cliente pelo CPF "<cpf>"
    Then eu recebo o status HTTP 200
    And os dados do cliente com CPF "<cpf>" são retornados

  # Cenário 3: Consultar cliente por CPF inexistente
  Scenario: Consultar cliente por CPF inexistente
    Given que não existe cliente cadastrado com CPF "63477336097"
    When eu consultar o cliente pelo CPF "63477336097"
    Then eu recebo o status HTTP 404

  # Cenário 4: Cadastrar novo cliente
  Scenario: Cadastrar um novo cliente
    Given que os dados do cliente são válidos
    When eu enviar uma requisição para cadastrar o cliente com CPF "57245755078"
    Then eu recebo o status HTTP 201
    And o cliente é retornado no corpo da resposta

  # Cenário 5: Tentar cadastrar cliente já existente
  Scenario: Tentar cadastrar cliente já existente
    Given que já existe um cliente cadastrado com CPF "12345678900"
    When eu enviar uma requisição para cadastrar o cliente com CPF existente "12345678900"
    Then eu recebo o status HTTP 400

  # Cenário 6: Atualizar cliente por código
  Scenario: Atualizar cliente por código
    Given que existe um cliente cadastrado com código "1"
    When eu enviar uma requisição para atualizar o cliente com código "1"
    Then eu recebo o status HTTP 200
    And os dados atualizados do cliente são retornados

  # Cenário 7: Atualizar cliente com código inexistente
  Scenario: Atualizar cliente com código inexistente
    Given que não existe cliente cadastrado com código "99"
    When eu enviar uma requisição para atualizar o cliente com código "99"
    Then eu recebo o status HTTP 404

  # Cenário 8: Remover cliente por código
  Scenario: Remover cliente por código
    Given que existe um cliente cadastrado com código "1"
    When eu enviar uma requisição para remover o cliente com código "1"
    Then eu recebo o status HTTP 204
    And o cliente com código "1" é removido

  # Cenário 9: Tentar remover cliente com código inexistente
  Scenario: Tentar remover cliente com código inexistente
    Given que não existe cliente cadastrado com código "99"
    When eu enviar uma requisição para remover o cliente inexistente com código "99"
    Then eu recebo o status HTTP 404
