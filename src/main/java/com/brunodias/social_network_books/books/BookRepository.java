package com.brunodias.social_network_books.books;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

/**
 * Interface de repositório para entidade Book, estendendo JpaRepository e JpaSpecificationExecutor.
 */
public interface BookRepository extends JpaRepository<Book, Integer>, JpaSpecificationExecutor<Book> {

    /**
     * Consulta personalizada para buscar todos os livros que são exibíveis para um usuário específico.
     *
     * @param pageable O objeto Pageable usado para paginação e ordenação.
     * @param userId   O ID do usuário que está solicitando a busca.
     * @return Uma página de livros que são exibíveis para o usuário.
     */
    @Query("""
            SELECT book
            FROM Book book
            WHERE book.archived = false
            AND book.shareable = true
            AND book.owner.id != :userId
            """)
    Page<Book> findAllDisplayableBooks(Pageable pageable, Integer userId);
}

