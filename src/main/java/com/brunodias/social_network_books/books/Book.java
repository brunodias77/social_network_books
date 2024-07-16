package com.brunodias.social_network_books.books;

import com.brunodias.social_network_books.commons.BaseEntity;
import com.brunodias.social_network_books.feedbacks.Feedback;
import com.brunodias.social_network_books.histories.BookTransactionHistory;
import com.brunodias.social_network_books.users.User;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Transient;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.util.List;

@Getter
@Setter
@SuperBuilder  // Lombok: Gera um padrão Builder para a classe
@AllArgsConstructor  // Lombok: Gera um construtor com todos os argumentos
@NoArgsConstructor  // Lombok: Gera um construtor sem argumentos
@Entity  // JPA: Indica que esta classe é uma entidade que será mapeada para uma tabela no banco de dados
public class Book extends BaseEntity {

    private String title;
    private String authorName;
    private String isbn;
    private String synopsis;   // sinopse
    private String bookCover;  // capa do livro
    private boolean archived;  // arquivado
    private boolean shareable; // compartilhave;

    @ManyToOne
    @JoinColumn(name = "owner_id")  // JPA: Especifica a coluna na tabela do banco de dados que armazena a chave estrangeira
    private User owner;  // Relacionamento muitos-para-um com a entidade User

    @OneToMany(mappedBy = "book")  // JPA: Indica um relacionamento um-para-muitos onde esta classe é o lado inverso (não dono) da relação
    private List<Feedback> feedbacks;  // Lista de feedbacks associados a este livro

    @OneToMany(mappedBy = "book")  // JPA: Outro relacionamento um-para-muitos com BookTransactionHistory
    private List<BookTransactionHistory> histories;  // Lista de históricos de transações associados a este livro

    @Transient  // JPA: Indica que este método não deve ser persistido no banco de dados
    public double getRate() {
        if (feedbacks == null || feedbacks.isEmpty()) {
            return 0.0;
        }
        var rate = this.feedbacks.stream()
                .mapToDouble(Feedback::getNote)
                .average()
                .orElse(0.0);
        double roundedRate = Math.round(rate * 10.0) / 10.0;

        // Return 4.0 if roundedRate is less than 4.5, otherwise return 4.5
        return roundedRate;
    }
}

