# ms-sistema-lanchonete-cliente
Microsserviço responsável pela criação e autenticação dos clientes do sistema-lanchonete (FIAP - Pós Tech)

## Contrato
  Com aplicação rodando, acesse o endpoint `/sistema-lanchonete-cliente/api/v1/swagger-ui/index.html` e você terá o detalhamento dos endpoints expostos na aplicação.

## A aplicação
  Conforme escrito anteriormente, podemos gerenciar e autenticar os clientes do sistema-lanchonete

### Clientes
  Os clientes (pessoas que compram da lanchonete) podem ter cpf, e-mail e nome.  
  Um cliente pode se cadastar utilizando apenas o cpf ou utilizando nome e e-mail.  
  O cadastro de um cliente possui um código que é utilizado para referencia-lo. 

### Desenho de Arquitetura dos Requisitos do Negócio:

### Componentes Principais:
  - Backend: microsserviço spring boot para o gerenciamento e autenticação dos clientes.
  - Banco de Dados: Armazenamento de dados de clientes.

### Serviços e Ferramentas AWS:
  - Amazon EC2: Para hospedar os microsserviço.
  - MongoDB: Banco de dados NoSQL para armazenamento dos clientes.
  - Network Load Balancer: Distribuição de tráfego para instâncias EC2.
  - Docker: Containerização dos microsserviços.

### Desenho de Arquitetura da Infraestrutura: 
![Diagrama - Arquitetura](https://github.com/user-attachments/assets/47b1b124-e7fe-47d2-bbe0-81a7937b3d91)

### Banco de Dados: 
Para a aplicação, foi utilizado como banco de dados uma instância do banco não relacional **MongoDB** através do serviço **MongoDB Atlas**. A escolha desse serviço ocorreu principalmente pela praticidade na configuração e gerenciamento do banco de dados por parte do **MongoDB Atlas**. 

### Autenticação: 
![Diagrama - Autenticação](https://github.com/kelvinlins/mssistemalanchonete/blob/a365165909f2cf20882c7c1b87fc8bc1a9e99ba5/assets/auth.jpg)

O usuário pode realizar o login utilizando o endpoint `/sistema-lanchonete-cliente/api/v1/auth` informando seu **CPF** e **senha** utilizados na realização do seu cadastro, caso o usuário tenha cadastro e suas informações estiverem corretas, será retornado um token JWT válido por 1 hora que deverá ser passado nas chamadas dos endpoints da aplicação. O API Gateway será responsável por executar a função lambda que realiza a validação do token JWT enviado nas requisições da aplicação, caso o mesmo seja válido, a requisição poderá ser feita com sucesso, caso contrario a aplicação retornará um erro de autenticação.

### Qualidade - (SonarQube):
[![Quality Gate Status](https://sonarcloud.io/api/project_badges/measure?project=Guimaj_ms-sistema-lanchonete-cliente&metric=alert_status)](https://sonarcloud.io/summary/new_code?id=Guimaj_ms-sistema-lanchonete-cliente)
[![Coverage](https://sonarcloud.io/api/project_badges/measure?project=Guimaj_ms-sistema-lanchonete-cliente&metric=coverage)](https://sonarcloud.io/summary/new_code?id=Guimaj_ms-sistema-lanchonete-cliente)
