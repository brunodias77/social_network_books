package com.brunodias.social_network_books.histories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface BookTransactionHistoryRepository extends JpaRepository<BookTransactionHistory, Integer> {

    // Verifica se um livro já foi emprestado para um usuário e não teve o retorno aprovado
    @Query("""
            SELECT
            (COUNT (*) > 0) AS isBorrowed
            FROM BookTransactionHistory bookTransactionHistory
            WHERE bookTransactionHistory.user.id = :userId
            AND bookTransactionHistory.book.id = :bookId
            AND bookTransactionHistory.returnApproved = false
            """)
    boolean isAlreadyBorrowedByUser(@Param("bookId") Integer bookId, @Param("userId") Integer userId);

    // Verifica se um livro já foi emprestado por qualquer usuário e não teve o retorno aprovado
    @Query("""
            SELECT
            (COUNT (*) > 0) AS isBorrowed
            FROM BookTransactionHistory bookTransactionHistory
            WHERE bookTransactionHistory.book.id = :bookId
            AND bookTransactionHistory.returnApproved = false
            """)
    boolean isAlreadyBorrowed(@Param("bookId") Integer bookId);

    // Busca transações não finalizadas de um livro por um usuário específico
    @Query("""
            SELECT transaction
            FROM BookTransactionHistory  transaction
            WHERE transaction.user.id = :userId
            AND transaction.book.id = :bookId
            AND transaction.returned = false
            AND transaction.returnApproved = false
            """)
    Optional<BookTransactionHistory> findByBookIdAndUserId(@Param("bookId") Integer bookId, @Param("userId") Integer userId);

    // Busca transações não finalizadas de um livro por seu proprietário
    @Query("""
            SELECT transaction
            FROM BookTransactionHistory  transaction
            WHERE transaction.book.owner.id = :userId
            AND transaction.book.id = :bookId
            AND transaction.returned = true
            AND transaction.returnApproved = false
            """)
    Optional<BookTransactionHistory> findByBookIdAndOwnerId(@Param("bookId") Integer bookId, @Param("userId") Integer userId);

    // Busca todas as transações de livros emprestados por um usuário específico
    @Query("""
            SELECT history
            FROM BookTransactionHistory history
            WHERE history.user.id = :userId
            """)
    Page<BookTransactionHistory> findAllBorrowedBooks(Pageable pageable, Integer userId);

    // Busca todas as transações de livros devolvidos por um usuário específico
    @Query("""
            SELECT history
            FROM BookTransactionHistory history
            WHERE history.book.owner.id = :userId
            """)
    Page<BookTransactionHistory> findAllReturnedBooks(Pageable pageable, Integer userId);
}

