package com.fiap.mssistemalanchonetecliente.bdd;

import com.fiap.mssistemalanchonetecliente.MssistemalanchoneteclienteApplication;
import com.fiap.mssistemalanchonetecliente.core.usecase.cliente.ClienteUseCase;
import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import io.cucumber.spring.CucumberContextConfiguration;
import org.junit.runner.RunWith;

import org.springframework.boot.test.autoconfigure.actuate.observability.AutoConfigureObservability;
import org.springframework.boot.test.autoconfigure.data.mongo.AutoConfigureDataMongo;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureWebMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;


@RunWith(Cucumber.class)
@CucumberContextConfiguration
@CucumberOptions(
		features = "src/test/resources/features",
		glue = "com.fiap.mssistemalanchonetecliente",
		plugin = {"pretty", "json:target/cucumber.json"}
)
@SpringBootTest(
		classes = MssistemalanchoneteclienteApplication.class,
		webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT
)
@AutoConfigureObservability
@AutoConfigureMockMvc
@AutoConfigureWebMvc
@ActiveProfiles("test")
public class CucumberTest {
	@MockBean
	ClienteUseCase clienteUseCase;
}
