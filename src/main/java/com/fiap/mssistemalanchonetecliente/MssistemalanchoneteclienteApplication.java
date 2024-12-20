package com.fiap.mssistemalanchonetecliente;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@OpenAPIDefinition(
		info = @Info(
				title = "sistema-lanchonete",
				description = "API responsavel pelo gerenciamento de lanchonete",
				version = "1.0"
		))
@SpringBootApplication
public class MssistemalanchoneteclienteApplication {

	public static void main(String[] args) {
		SpringApplication.run(MssistemalanchoneteclienteApplication.class, args);
	}

}
