package com.eisenguide2;

import java.net.URI;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@RestController
@RequestMapping("/")
public class Eisenguide2Application {
	
	@GetMapping("/")
    public ResponseEntity<Void> redirectToSwaggerUi() {
        // Redireciona diretamente para o Swagger UI
        return ResponseEntity.status(HttpStatus.FOUND)
                             .location(URI.create("/swagger-ui/index.html"))
                             .build();
    }

	public static void main(String[] args) {
		SpringApplication.run(Eisenguide2Application.class, args);
	}

}
