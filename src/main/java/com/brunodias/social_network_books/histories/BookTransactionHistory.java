package com.brunodias.social_network_books.histories;

import com.brunodias.social_network_books.books.Book;
import com.brunodias.social_network_books.commons.BaseEntity;
import com.brunodias.social_network_books.users.User;
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
public class BookTransactionHistory extends BaseEntity {

    @ManyToOne  // JPA: Define um relacionamento muitos-para-um com a entidade User
    @JoinColumn(name = "user_id")  // JPA: Especifica a coluna na tabela do banco de dados que armazena a chave estrangeira para o usuário
    private User user;  // Usuário envolvido nesta transação de livro

    @ManyToOne  // JPA: Define um relacionamento muitos-para-um com a entidade Book
    @JoinColumn(name = "book_id")  // JPA: Especifica a coluna na tabela do banco de dados que armazena a chave estrangeira para o livro
    private Book book;  // Livro envolvido nesta transação de livro

    private boolean returned;  // Indica se o livro foi devolvido nesta transação
    private boolean returnApproved;  // Indica se a devolução do livro foi aprovada
}

