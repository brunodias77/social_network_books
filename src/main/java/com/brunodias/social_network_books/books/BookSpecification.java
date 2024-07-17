package com.brunodias.social_network_books.books;

import org.springframework.data.jpa.domain.Specification;

// Classe que define especificações para consultas na entidade Book.
public class BookSpecification {

    // Método estático que retorna uma especificação para filtrar livros por ID do proprietário.
    public static Specification<Book> withOwnerId(Integer ownerId) {
        // Retorna uma Specification<Book> usando uma expressão lambda.
        // O parâmetro 'root' representa a entidade root da consulta (neste caso, 'Book').
        // O parâmetro 'query' representa a consulta sendo construída.
        // O parâmetro 'criteriaBuilder' é utilizado para construir os critérios de consulta.
        return (root, query, criteriaBuilder) ->
                // Constrói um predicado que verifica se o ID do proprietário é igual ao 'ownerId' fornecido.
                criteriaBuilder.equal(root.get("owner").get("id"), ownerId);
    }
}

