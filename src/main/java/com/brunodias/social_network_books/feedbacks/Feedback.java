package com.brunodias.social_network_books.feedbacks;

import com.brunodias.social_network_books.books.Book;
import com.brunodias.social_network_books.commons.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@SuperBuilder  // Lombok: Gera um padrão Builder para a classe
@AllArgsConstructor  // Lombok: Gera um construtor com todos os argumentos
@NoArgsConstructor  // Lombok: Gera um construtor sem argumentos
@Entity  // JPA: Indica que esta classe é uma entidade que será mapeada para uma tabela no banco de dados
public class Feedback extends BaseEntity {

    @Column  // JPA: Mapeia o atributo para uma coluna no banco de dados
    private Double note;  // Avaliação numérica atribuída ao feedback 1-5 stars

    private String comment;  // Comentário associado ao feedback

    @ManyToOne  // JPA: Define um relacionamento muitos-para-um com a entidade Book
    @JoinColumn(name = "book_id")  // JPA: Especifica a coluna na tabela do banco de dados que armazena a chave estrangeira
    private Book book;  // Livro ao qual este feedback está associado
}

