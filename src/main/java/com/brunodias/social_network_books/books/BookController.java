package com.brunodias.social_network_books.books;

import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import org.springframework.web.multipart.MultipartFile;


@RestController
@RequestMapping("books")
@RequiredArgsConstructor
@Tag(name = "Book")
public class BookController {

    private final BookService service;

    // Endpoint para salvar um livro
    @PostMapping
    public ResponseEntity<Integer> saveBook(
            @Valid @RequestBody BookRequest request,
            Authentication connectedUser
    ) {
        return ResponseEntity.ok(service.save(request, connectedUser));
    }

//    // Endpoint para buscar um livro pelo ID
//    @GetMapping("/{book-id}")
//    public ResponseEntity<BookResponse> findBookById(
//            @PathVariable("book-id") Integer bookId
//    ) {
//        return ResponseEntity.ok(service.findById(bookId));
//    }
//
//    // Endpoint para buscar todos os livros paginados
//    @GetMapping
//    public ResponseEntity<PageResponse<BookResponse>> findAllBooks(
//            @RequestParam(name = "page", defaultValue = "0", required = false) int page,
//            @RequestParam(name = "size", defaultValue = "10", required = false) int size,
//            Authentication connectedUser
//    ) {
//        return ResponseEntity.ok(service.findAllBooks(page, size, connectedUser));
//    }
//
//    // Endpoint para buscar todos os livros de um proprietário paginados
//    @GetMapping("/owner")
//    public ResponseEntity<PageResponse<BookResponse>> findAllBooksByOwner(
//            @RequestParam(name = "page", defaultValue = "0", required = false) int page,
//            @RequestParam(name = "size", defaultValue = "10", required = false) int size,
//            Authentication connectedUser
//    ) {
//        return ResponseEntity.ok(service.findAllBooksByOwner(page, size, connectedUser));
//    }
//
//    // Endpoint para buscar todos os livros emprestados paginados
//    @GetMapping("/borrowed")
//    public ResponseEntity<PageResponse<BorrowedBookResponse>> findAllBorrowedBooks(
//            @RequestParam(name = "page", defaultValue = "0", required = false) int page,
//            @RequestParam(name = "size", defaultValue = "10", required = false) int size,
//            Authentication connectedUser
//    ) {
//        return ResponseEntity.ok(service.findAllBorrowedBooks(page, size, connectedUser));
//    }
//
//    // Endpoint para buscar todos os livros devolvidos paginados
//    @GetMapping("/returned")
//    public ResponseEntity<PageResponse<BorrowedBookResponse>> findAllReturnedBooks(
//            @RequestParam(name = "page", defaultValue = "0", required = false) int page,
//            @RequestParam(name = "size", defaultValue = "10", required = false) int size,
//            Authentication connectedUser
//    ) {
//        return ResponseEntity.ok(service.findAllReturnedBooks(page, size, connectedUser));
//    }
//
//    // Endpoint para atualizar o status de compartilhamento de um livro
//    @PatchMapping("/shareable/{book-id}")
//    public ResponseEntity<Integer> updateShareableStatus(
//            @PathVariable("book-id") Integer bookId,
//            Authentication connectedUser
//    ) {
//        return ResponseEntity.ok(service.updateShareableStatus(bookId, connectedUser));
//    }
//
//    // Endpoint para atualizar o status de arquivamento de um livro
//    @PatchMapping("/archived/{book-id}")
//    public ResponseEntity<Integer> updateArchivedStatus(
//            @PathVariable("book-id") Integer bookId,
//            Authentication connectedUser
//    ) {
//        return ResponseEntity.ok(service.updateArchivedStatus(bookId, connectedUser));
//    }
//
//    // Endpoint para emprestar um livro
//    @PostMapping("borrow/{book-id}")
//    public ResponseEntity<Integer> borrowBook(
//            @PathVariable("book-id") Integer bookId,
//            Authentication connectedUser
//    ) {
//        return ResponseEntity.ok(service.borrowBook(bookId, connectedUser));
//    }
//
//    // Endpoint para devolver um livro emprestado
//    @PatchMapping("borrow/return/{book-id}")
//    public ResponseEntity<Integer> returnBorrowBook(
//            @PathVariable("book-id") Integer bookId,
//            Authentication connectedUser
//    ) {
//        return ResponseEntity.ok(service.returnBorrowedBook(bookId, connectedUser));
//    }
//
//    // Endpoint para aprovar a devolução de um livro emprestado
//    @PatchMapping("borrow/return/approve/{book-id}")
//    public ResponseEntity<Integer> approveReturnBorrowBook(
//            @PathVariable("book-id") Integer bookId,
//            Authentication connectedUser
//    ) {
//        return ResponseEntity.ok(service.approveReturnBorrowedBook(bookId, connectedUser));
//    }
//
//    // Endpoint para fazer upload da capa de um livro
//    @PostMapping(value = "/cover/{book-id}", consumes = "multipart/form-data")
//    public ResponseEntity<?> uploadBookCoverPicture(
//            @PathVariable("book-id") Integer bookId,
//            @Parameter()
//            @RequestPart("file") MultipartFile file,
//            Authentication connectedUser
//    ) {
//        service.uploadBookCoverPicture(file, connectedUser, bookId);
//        return ResponseEntity.accepted().build();
//    }
}

