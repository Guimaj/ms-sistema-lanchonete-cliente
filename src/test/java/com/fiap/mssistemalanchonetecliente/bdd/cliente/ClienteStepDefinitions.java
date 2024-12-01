package com.fiap.mssistemalanchonetecliente.bdd.cliente;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fiap.mssistemalanchonetecliente.core.exception.exception.ClienteAlreadyExistsException;
import com.fiap.mssistemalanchonetecliente.core.exception.exception.ClienteNotFoundException;
import com.fiap.mssistemalanchonetecliente.core.model.Cliente;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.AutoConfigureDataMongo;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.util.Collections;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@AutoConfigureDataMongo
public class ClienteStepDefinitions {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private com.fiap.mssistemalanchonetecliente.core.usecase.cliente.ClienteUseCase clienteUseCase;

    @Autowired
    private ObjectMapper objectMapper;

    private ResultActions response;
    private Cliente cliente;

    private void criarCliente(String codigo, String cpf, String nome, String email, String senha) {
        cliente = new Cliente(codigo, cpf, senha);
        cliente.setNome(nome);
        cliente.setEmail(email);
    }

    @Given("que existem clientes cadastrados")
    public void queExistemClientesCadastrados() {
        criarCliente("1", "12345678900", "João Silva", "joao.silva@example.com", "senha123");
        Mockito.when(clienteUseCase.consultarTodosClientes()).thenReturn(Collections.singletonList(cliente));
    }

    @Given("que existe um cliente cadastrado com CPF {string}")
    public void queExisteUmClienteCadastradoComCPF(String cpf) {
        criarCliente("1", cpf, "Maria Oliveira", "maria.oliveira@example.com", "senha123");
        Mockito.when(clienteUseCase.consultarClientePorCpf(eq(cpf))).thenReturn(cliente);
    }

    @Given("que não existe cliente cadastrado com CPF {string}")
    public void queNaoExisteClienteCadastradoComCPF(String cpf) {
        Mockito.when(clienteUseCase.consultarClientePorCpf(eq(cpf)))
                .thenThrow(new ClienteNotFoundException(cpf));
    }

    @Given("que os dados do cliente são válidos")
    public void queOsDadosDoClienteSaoValidos() {
        criarCliente("1", "57245755078", "Carlos Almeida", "carlos.almeida@example.com", "senha123");
    }

    @Given("que já existe um cliente cadastrado com CPF {string}")
    public void queJaExisteUmClienteCadastradoComCPF(String cpf) {
        Mockito.when(clienteUseCase.salvarCliente(any(Cliente.class)))
                .thenThrow(new RuntimeException("Cliente já possui cadastro!"));
    }

    @Given("que existe um cliente cadastrado com código {string}")
    public void queExisteUmClienteCadastradoComCodigo(String codigo) {
        criarCliente(codigo, "57245755078", "Carlos Almeida", "carlos.almeida@example.com", "senha123");
        Mockito.when(clienteUseCase.atualizarCliente(any(Cliente.class), eq(codigo)))
                .thenReturn(new Cliente(codigo, cliente.getCpf(), "Carlos Atualizado", "carlos.atualizado@example.com", cliente.getSenha()));
    }

    @Given("que não existe cliente cadastrado com código {string}")
    public void queNaoExisteClienteCadastradoComCodigo(String codigo) {
        Mockito.when(clienteUseCase.atualizarCliente(any(Cliente.class), eq(codigo)))
                .thenThrow(new ClienteNotFoundException(String.valueOf(codigo)));
    }

    @When("eu consultar todos os clientes")
    public void euConsultarTodosOsClientes() throws Exception {
        response = mockMvc.perform(get("/clientes")
                .contentType(MediaType.APPLICATION_JSON));
    }

    @When("eu consultar o cliente pelo CPF {string}")
    public void euConsultarOClientePeloCPF(String cpf) throws Exception {
        response = mockMvc.perform(get("/clientes/{cpf}", cpf)
                .contentType(MediaType.APPLICATION_JSON));
    }

    @When("eu enviar uma requisição para cadastrar o cliente com CPF {string}")
    public void euEnviarUmaRequisicaoParaCadastrarOClienteComCPF(String cpf) throws Exception {
        Mockito.when(clienteUseCase.salvarCliente(any(Cliente.class)))
                .thenReturn(cliente);

        response = mockMvc.perform(post("/clientes")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(cliente)));
    }

    @When("eu enviar uma requisição para cadastrar o cliente com CPF existente {string}")
    public void euEnviarUmaRequisicaoParaCadastrarOClienteComCPFExistente(String cpf) throws Exception {
        Mockito.when(clienteUseCase.salvarCliente(any(Cliente.class)))
                .thenThrow(new ClienteAlreadyExistsException(cpf));

        response = mockMvc.perform(post("/clientes")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(cliente)));
    }

    @When("eu enviar uma requisição para atualizar o cliente com código {string}")
    public void euEnviarUmaRequisicaoParaAtualizarOClienteComCodigo(String codigo) throws Exception {
        criarCliente(codigo, "10445065044", "Teste teste", "teste.teste@example.com", "senha123");
        response = mockMvc.perform(patch("/clientes/{codigo}", codigo)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(cliente)));
    }

    @When("eu enviar uma requisição para remover o cliente com código {string}")
    public void euEnviarUmaRequisicaoParaRemoverOClienteComCodigo(String codigo) throws Exception {
        Mockito.doNothing().when(clienteUseCase)
                .deletarCliente(eq(codigo));

        response = mockMvc.perform(delete("/clientes/{codigo}", codigo)
                .contentType(MediaType.APPLICATION_JSON));
    }

    @When("eu enviar uma requisição para remover o cliente inexistente com código {string}")
    public void euEnviarUmaRequisicaoParaRemoverOClienteInexistenteComCodigo(String codigo) throws Exception {
        Mockito.doThrow(new ClienteNotFoundException(String.valueOf(codigo)))
                .when(clienteUseCase)
                .deletarCliente(eq(codigo));

        response = mockMvc.perform(delete("/clientes/{codigo}", codigo)
                .contentType(MediaType.APPLICATION_JSON));
    }

    @Then("eu recebo o status HTTP {int}")
    public void euReceboOStatusHTTP(int statusCode) throws Exception {
        response.andExpect(status().is(statusCode));
    }

    @Then("a lista de clientes não é vazia")
    public void aListaDeClientesNaoEVazia() throws Exception {
        response.andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$[0].cpf").value("12345678900"));
    }

    @Then("os dados do cliente com CPF {string} são retornados")
    public void osDadosDoClienteComCPFSaoRetornados(String cpf) throws Exception {
        response.andExpect(jsonPath("$.cpf").value(cpf));
    }

    @Then("o cliente é retornado no corpo da resposta")
    public void oClienteERetornadoNoCorpoDaResposta() throws Exception {
        response.andExpect(status().isCreated())
                .andExpect(jsonPath("$.cpf").value(cliente.getCpf()))
                .andExpect(jsonPath("$.nome").value(cliente.getNome()));
    }

    @Then("a mensagem de erro {string} é retornada")
    public void aMensagemDeErroERetornada(String mensagem) throws Exception {
        response.andExpect(jsonPath("$.message").value(mensagem));
    }

    @Then("os dados atualizados do cliente são retornados")
    public void osDadosAtualizadosDoClienteSaoRetornados() throws Exception {
        response.andExpect(status().isOk())
                .andExpect(jsonPath("$.nome").value("Carlos Atualizado"))
                .andExpect(jsonPath("$.email").value("carlos.atualizado@example.com"));
    }

    @Then("o cliente com código {string} é removido")
    public void oClienteComCodigoERemovido(String codigo) throws Exception {
        response.andExpect(status().isNoContent());
    }
}
