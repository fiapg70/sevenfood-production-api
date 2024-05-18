package br.com.postech.sevenfoodproduction.sevenfoodproductionapi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@EnableMongoRepositories(basePackages = "br.com.postech.sevenfoodproduction.sevenfoodproductionapi.model.repositories")
@SpringBootApplication
public class SevenfoodProductionApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(SevenfoodProductionApiApplication.class, args);
	}

}
