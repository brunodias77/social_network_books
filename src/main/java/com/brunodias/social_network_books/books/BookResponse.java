package com.brunodias.social_network_books.books;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

// Annotations do Lombok para geração automática de código
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BookResponse {

    private Integer id;         // Identificador do livro
    private String title;       // Título do livro
    private String authorName;  // Nome do autor do livro
    private String isbn;        // ISBN do livro
    private String synopsis;    // Sinopse do livro
    private String owner;       // Proprietário do livro
    private byte[] cover;       // Capa do livro (array de bytes)
    private double rate;        // Avaliação do livro
    private boolean archived;   // Indica se o livro está arquivado
    private boolean shareable;  // Indica se o livro é compartilhável

}

