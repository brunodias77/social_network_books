package com.brunodias.social_network_books.handler;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Map;
import java.util.Set;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class ExceptionResponse {

    // A anotação @Getter do Lombok gera automaticamente os métodos getters para os campos privados.
    // A anotação @Setter do Lombok gera automaticamente os métodos setters para os campos privados.
    // Essas anotações reduzem a quantidade de código boilerplate.

    private Integer businessErrorCode;
    private String businessErrorDescription;
    private String error;
    private Set<String> validationErrors;
    private Map<String, String> errors;

    // A anotação @Builder do Lombok gera um padrão Builder para a classe.
    // Isso permite construir objetos usando um estilo fluente, como:
    // ExceptionResponse.builder().businessErrorCode(123).error("Some error").build();

    // A anotação @AllArgsConstructor do Lombok gera um construtor com todos os campos da classe como argumentos.
    // Este construtor é útil para inicializar rapidamente objetos da classe com todos os campos.

    // A anotação @NoArgsConstructor do Lombok gera um construtor sem argumentos.
    // Esse construtor é usado principalmente por frameworks como o Spring Boot para criar objetos da classe por reflexão.

    // A anotação @JsonInclude(JsonInclude.Include.NON_EMPTY) do Jackson controla a serialização para JSON.
    // Neste caso, ela instrui o Jackson a incluir apenas propriedades que não são nulas ou vazias ao serializar o objeto.
    // Isso evita a serialização de campos que não têm valor, como `null`, conjuntos vazios ou mapas vazios.
}
