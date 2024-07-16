package com.brunodias.social_network_books.auth;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter // Gera métodos getters para todos os campos da classe
@Setter // Gera métodos setters para todos os campos da classe
@Builder // Gera um padrão de design Builder para a classe
public class AuthenticationRequest {

    @Email(message = "email não possui um formato de e-mail válido") // Valida se o campo email possui um formato de e-mail válido
    @NotEmpty(message = "E-mail é obrigatório") // Valida se o campo email não está vazio
    @NotNull(message = "E-mail é obrigatório") // Valida se o campo email não é nulo
    private String email;

    @NotEmpty(message = "Senha é obrigatória") // Valida se o campo password não está vazio
    @NotNull(message = "Senha é obrigatória") // Valida se o campo password não é nulo
    @Size(min = 8, message = "A senha deve ter no mínimo 8 caracteres") // Valida se o campo password possui pelo menos 8 caracteres
    private String password;
}
