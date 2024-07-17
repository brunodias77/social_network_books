package com.brunodias.social_network_books.feedbacks;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

// Interface que representa o repositório de Feedbacks.
// Estende JpaRepository para fornecer métodos CRUD básicos.
public interface FeedBackRepository extends JpaRepository<Feedback, Integer> {

    // Consulta personalizada para buscar Feedbacks por ID do livro com paginação.
    // A anotação @Query permite definir consultas JPQL (Java Persistence Query Language).
    @Query("""
            SELECT feedback
            FROM Feedback feedback
            WHERE feedback.book.id = :bookId
    """)
    // Método que retorna uma página de Feedbacks para um dado ID de livro.
    // O @Param("bookId") é usado para vincular o parâmetro do método à consulta JPQL.
    Page<Feedback> findAllByBookId(@Param("bookId") Integer bookId, Pageable pageable);
}
