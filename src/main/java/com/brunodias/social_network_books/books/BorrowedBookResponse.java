package com.brunodias.social_network_books.books;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

// Lombok cria automaticamente os getters para todos os campos da classe.
@Getter
// Lombok cria automaticamente os setters para todos os campos da classe.
@Setter
// Lombok cria um construtor com todos os argumentos (todos os campos da classe).
@AllArgsConstructor
// Lombok cria um construtor sem argumentos (construtor padrão).
@NoArgsConstructor
// Lombok fornece um construtor com base no padrão de projeto Builder.
@Builder
public class BorrowedBookResponse {

    // ID do livro emprestado.
    private Integer id;

    // Título do livro.
    private String title;

    // Nome do autor do livro.
    private String authorName;

    // ISBN do livro.
    private String isbn;

    // Avaliação do livro.
    private double rate;

    // Indica se o livro foi devolvido.
    private boolean returned;

    // Indica se a devolução do livro foi aprovada.
    private boolean returnApproved;
}

