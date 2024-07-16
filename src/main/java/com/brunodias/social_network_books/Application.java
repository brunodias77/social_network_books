package com.brunodias.social_network_books;

import com.brunodias.social_network_books.role.Role;
import com.brunodias.social_network_books.role.RoleRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication // Indica que esta é uma aplicação Spring Boot
@EnableJpaAuditing(auditorAwareRef = "auditorAware") // Habilita a auditoria automática do JPA
@EnableAsync // Habilita o suporte a execuções assíncronas
public class Application {

	public static void main(String[] args) {

		SpringApplication.run(Application.class, args);
	}

	@Bean // Indica que este método produz um bean gerenciado pelo Spring
	public CommandLineRunner runner(RoleRepository roleRepository) {
		return args -> {
			if (roleRepository.findByName("USER").isEmpty()) { // Verifica se o papel "USER" não existe
				roleRepository.save(Role.builder().name("USER").build()); // Cria e salva o papel "USER"
			}
		};
	}


}
