package com.brunodias.social_network_books.handler;

import lombok.Getter;
import org.springframework.http.HttpStatus;

import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.FORBIDDEN;
import static org.springframework.http.HttpStatus.NOT_IMPLEMENTED;

public enum BusinessErrorCodes {

    // Enumerações definindo códigos de erro de negócio com seus detalhes correspondentes.

    NO_CODE(0, NOT_IMPLEMENTED, "Sem código"),
    INCORRECT_CURRENT_PASSWORD(300, BAD_REQUEST, "A senha atual está incorreta"),
    NEW_PASSWORD_DOES_NOT_MATCH(301, BAD_REQUEST, "A nova senha não corresponde"),
    ACCOUNT_LOCKED(302, FORBIDDEN, "Conta de usuário bloqueada"),
    ACCOUNT_DISABLED(303, FORBIDDEN, "Conta de usuário desativada"),
    BAD_CREDENTIALS(304, FORBIDDEN, "Login e/ou Senha incorretos"),
    ;

    // A anotação @Getter do Lombok gera automaticamente os métodos getters para os campos privados.

    @Getter
    private final int code;           // Código do erro.
    @Getter
    private final String description; // Descrição do erro.
    @Getter
    private final HttpStatus httpStatus; // Status HTTP associado ao erro.

    // Construtor privado usado para inicializar cada enum com os valores correspondentes.
    BusinessErrorCodes(int code, HttpStatus status, String description) {
        this.code = code;
        this.description = description;
        this.httpStatus = status;
    }
}

