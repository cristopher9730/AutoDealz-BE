package com.autos;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
@EnableConfigurationProperties(AppProperties.class)
public class AgenciaAutosUsadosApplication {

	public static void main(String[] args) {
		SpringApplication.run(AgenciaAutosUsadosApplication.class, args);
	}

}
