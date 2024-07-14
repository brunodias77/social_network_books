package com.brunodias.social_network_books.auth;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController // Indica que esta classe é um controlador REST
@RequestMapping("auth") // Mapeia todas as URLs iniciadas com "/auth" para este controlador
@RequiredArgsConstructor // Gera um construtor com argumentos para todos os campos final na classe
@Tag(name = "Authentication") // Anotação do Swagger para descrever a tag de Swagger para este controlador
public class AuthenticationController {

    private final AuthenticationService service;

}
