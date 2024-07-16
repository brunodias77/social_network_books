package com.brunodias.social_network_books.books;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

// Definição do record BookRequest
public record BookRequest(
        Integer id, // Campo para o ID do livro
        @NotNull(message = "100")
        @NotEmpty(message = "100")
        String title, // Campo para o título do livro, com validações de not null e not empty
        @NotNull(message = "101")
        @NotEmpty(message = "101")
        String authorName, // Campo para o nome do autor do livro, com validações de not null e not empty
        @NotNull(message = "102")
        @NotEmpty(message = "102")
        String isbn, // Campo para o ISBN do livro, com validações de not null e not empty
        @NotNull(message = "103")
        @NotEmpty(message = "103")
        String synopsis, // Campo para a sinopse do livro, com validações de not null e not empty
        boolean shareable // Campo booleano indicando se o livro é compartilhável
) {
}

