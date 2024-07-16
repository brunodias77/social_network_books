package com.brunodias.social_network_books.books;

import com.brunodias.social_network_books.files.FileUtils;
import org.springframework.stereotype.Service;

@Service
public class BookMapper {

    // Converte um objeto BookRequest em um objeto Book
    public Book toBook(BookRequest request) {
        return Book.builder()
                .id(request.id())               // Define o ID do livro
                .title(request.title())         // Define o título do livro
                .isbn(request.isbn())           // Define o ISBN do livro
                .authorName(request.authorName()) // Define o nome do autor do livro
                .synopsis(request.synopsis())   // Define a sinopse do livro
                .archived(false)                // Define se o livro está arquivado como falso
                .shareable(request.shareable()) // Define se o livro pode ser compartilhado
                .build();                       // Constrói o objeto Book
    }

    // Converte um objeto Book em um objeto BookResponse
    public BookResponse toBookResponse(Book book) {
        return BookResponse.builder()
                .id(book.getId())               // Define o ID do livro
                .title(book.getTitle())         // Define o título do livro
                .authorName(book.getAuthorName()) // Define o nome do autor do livro
                .isbn(book.getIsbn())           // Define o ISBN do livro
                .synopsis(book.getSynopsis())   // Define a sinopse do livro
                .rate(book.getRate())           // Define a avaliação do livro
                .archived(book.isArchived())    // Define se o livro está arquivado
                .shareable(book.isShareable())  // Define se o livro pode ser compartilhado
                .owner(book.getOwner().fullName()) // Define o nome completo do proprietário do livro
                .cover(FileUtils.readFileFromLocation(book.getBookCover())) // Define a capa do livro lendo do local especificado
                .build();                       // Constrói o objeto BookResponse
    }

    // Converte um objeto BookTransactionHistory em um objeto BorrowedBookResponse
//    public BorrowedBookResponse toBorrowedBookResponse(BookTransactionHistory history) {
//        return BorrowedBookResponse.builder()
//                .id(history.getBook().getId())           // Define o ID do livro emprestado
//                .title(history.getBook().getTitle())     // Define o título do livro emprestado
//                .authorName(history.getBook().getAuthorName()) // Define o nome do autor do livro emprestado
//                .isbn(history.getBook().getIsbn())       // Define o ISBN do livro emprestado
//                .rate(history.getBook().getRate())       // Define a avaliação do livro emprestado
//                .returned(history.isReturned())          // Define se o livro foi devolvido
//                .returnApproved(history.isReturnApproved()) // Define se a devolução do livro foi aprovada
//                .build();                               // Constrói o objeto BorrowedBookResponse
//    }
}

