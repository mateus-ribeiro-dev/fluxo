package br.com.fiap.fluxo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@EnableCaching
@SpringBootApplication
public class FluxoApplication {

	public static void main(String[] args) {
		SpringApplication.run(FluxoApplication.class, args);
	}

}
