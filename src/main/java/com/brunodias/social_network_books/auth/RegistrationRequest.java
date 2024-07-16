package com.brunodias.social_network_books.auth;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter  // Anotação do Lombok para gerar métodos getters para todos os campos
@Setter  // Anotação do Lombok para gerar métodos setters para todos os campos
@Builder // Anotação do Lombok para implementar o padrão Builder para esta classe
public class RegistrationRequest {

    @NotEmpty(message = "O nome é obrigatório")
    // @NotEmpty: Garante que o campo não seja nulo e que seu comprimento seja maior que zero.
    // Esta validação será acionada se o campo estiver vazio (ou seja, "").
    @NotNull(message = "O nome é obrigatório !")
    // @NotNull: Garante que o campo não seja nulo. Esta validação será acionada se o campo for nulo.
    private String firstname;

    @NotEmpty(message = "O sobrenome é obrigatório")
    // @NotEmpty: Garante que o campo não seja nulo e que seu comprimento seja maior que zero.
    // Esta validação será acionada se o campo estiver vazio (ou seja, "").
    @NotNull(message = "O sobrenome é obrigatório")
    // @NotNull: Garante que o campo não seja nulo. Esta validação será acionada se o campo for nulo.
    private String lastname;

    @Email(message = "E-mail esta em um formato invalido")
    // @Email: Garante que o campo tenha um formato de email válido. Esta validação será acionada
    // se o campo não corresponder ao padrão de email.
    @NotEmpty(message = "Email é obrigatório")
    // @NotEmpty: Garante que o campo não seja nulo e que seu comprimento seja maior que zero.
    // Esta validação será acionada se o campo estiver vazio (ou seja, "").
    @NotNull(message = "Email é obrigatório")
    // @NotNull: Garante que o campo não seja nulo. Esta validação será acionada se o campo for nulo.
    private String email;

    @NotEmpty(message = "Senha é obrigatória")
    // @NotEmpty: Garante que o campo não seja nulo e que seu comprimento seja maior que zero.
    // Esta validação será acionada se o campo estiver vazio (ou seja, "").
    @NotNull(message = "Senha é obrigatória")
    // @NotNull: Garante que o campo não seja nulo. Esta validação será acionada se o campo for nulo.
    @Size(min = 8, message = "A senha deve ter no mínimo 8 caracteres")
    // @Size: Garante que o campo tenha um comprimento dentro do intervalo especificado. Aqui, garante
    // que a senha tenha pelo menos 8 caracteres.
    private String password;
}