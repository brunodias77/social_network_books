package com.brunodias.social_network_books.books;

import com.brunodias.social_network_books.commons.PageResponse;
import com.brunodias.social_network_books.exceptions.OperationNotPermittedException;
import com.brunodias.social_network_books.files.FileStorageService;
import com.brunodias.social_network_books.histories.BookTransactionHistory;
import com.brunodias.social_network_books.histories.BookTransactionHistoryRepository;
import com.brunodias.social_network_books.users.User;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Objects;

import static com.brunodias.social_network_books.books.BookSpecification.withOwnerId;


@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class BookService {

    private final BookRepository bookRepository;  // Injeção de dependência do repositório de livros
    private final BookMapper bookMapper;  // Injeção de dependência do mapper de livros
    private final BookTransactionHistoryRepository transactionHistoryRepository;  // Injeção de dependência do repositório de histórico de transações de livros
    private final FileStorageService fileStorageService;  // Injeção de dependência do serviço de armazenamento de arquivos

    public Integer save(BookRequest request, Authentication connectedUser) {
        User user = ((User) connectedUser.getPrincipal());  // Obtém o usuário autenticado
        Book book = bookMapper.toBook(request);  // Mapeia os dados do request para uma entidade Book
        book.setOwner(user);  // Define o proprietário do livro como o usuário autenticado
        return this.bookRepository.save(book).getId();  // Salva o livro no banco de dados e retorna o ID do livro salvo
    }

    // Método para buscar um livro pelo ID
    public BookResponse findById(Integer bookId) {
        return this.bookRepository.findById(bookId)  // Busca um livro pelo ID
                .map(bookMapper::toBookResponse)  // Converte o livro encontrado para um BookResponse usando o mapper
                .orElseThrow(() -> new EntityNotFoundException("No book found with ID:: " + bookId));  // Lança uma exceção caso o livro não seja encontrado
    }

    // Método para buscar todos os livros com paginação
    public PageResponse<BookResponse> findAllBooks(int page, int size, Authentication connectedUser) {
        User user = ((User) connectedUser.getPrincipal());  // Obtém o usuário autenticado
        Pageable pageable = PageRequest.of(page, size, Sort.by("createdDate").descending());  // Configura a paginação e ordenação por data de criação descendente
        Page<Book> books = this.bookRepository.findAllDisplayableBooks(pageable, user.getId());  // Busca os livros disponíveis para exibição pelo ID do usuário
        List<BookResponse> booksResponse = books.stream()  // Converte os livros encontrados para BookResponse usando o mapper
                .map(bookMapper::toBookResponse)
                .toList();
        return new PageResponse<>(  // Retorna uma resposta paginada de livros
                booksResponse,
                books.getNumber(),
                books.getSize(),
                books.getTotalElements(),
                books.getTotalPages(),
                books.isFirst(),
                books.isLast()
        );
    }

//    // Método para buscar todos os livros de um usuário específico com paginação
    public PageResponse<BookResponse> findAllBooksByOwner(int page, int size, Authentication connectedUser) {
        User user = ((User) connectedUser.getPrincipal());  // Obtém o usuário autenticado
        Pageable pageable = PageRequest.of(page, size, Sort.by("createdDate").descending());  // Configura a paginação e ordenação por data de criação descendente
        Page<Book> books = this.bookRepository.findAll(withOwnerId(user.getId()), pageable);  // Busca os livros do usuário pelo ID do proprietário
        List<BookResponse> booksResponse = books.stream()  // Converte os livros encontrados para BookResponse usando o mapper
                .map(bookMapper::toBookResponse)
                .toList();
        return new PageResponse<>(  // Retorna uma resposta paginada de livros
                booksResponse,
                books.getNumber(),
                books.getSize(),
                books.getTotalElements(),
                books.getTotalPages(),
                books.isFirst(),
                books.isLast()
        );
    }

    // Método para buscar todos os livros emprestados pelo usuário autenticado com paginação
    public PageResponse<BorrowedBookResponse> findAllBorrowedBooks(int page, int size, Authentication connectedUser) {
        User user = ((User) connectedUser.getPrincipal());  // Obtém o usuário autenticado
        Pageable pageable = PageRequest.of(page, size, Sort.by("createdDate").descending());  // Configura a paginação e ordenação por data de criação descendente
        Page<BookTransactionHistory> allBorrowedBooks = transactionHistoryRepository.findAllBorrowedBooks(pageable, user.getId());  // Busca todos os livros emprestados pelo usuário
        List<BorrowedBookResponse> booksResponse = allBorrowedBooks.stream()  // Converte os livros encontrados para BorrowedBookResponse usando o mapper
                .map(bookMapper::toBorrowedBookResponse)
                .toList();
        return new PageResponse<>(  // Retorna uma resposta paginada de livros emprestados
                booksResponse,
                allBorrowedBooks.getNumber(),
                allBorrowedBooks.getSize(),
                allBorrowedBooks.getTotalElements(),
                allBorrowedBooks.getTotalPages(),
                allBorrowedBooks.isFirst(),
                allBorrowedBooks.isLast()
        );
    }

    // Método para buscar todos os livros devolvidos pelo usuário autenticado com paginação
    public PageResponse<BorrowedBookResponse> findAllReturnedBooks(int page, int size, Authentication connectedUser) {
        User user = ((User) connectedUser.getPrincipal());  // Obtém o usuário autenticado
        Pageable pageable = PageRequest.of(page, size, Sort.by("createdDate").descending());  // Configura a paginação e ordenação por data de criação descendente
        Page<BookTransactionHistory> allBorrowedBooks = this.transactionHistoryRepository.findAllReturnedBooks(pageable, user.getId());  // Busca todos os livros devolvidos pelo usuário
        List<BorrowedBookResponse> booksResponse = allBorrowedBooks.stream()  // Converte os livros encontrados para BorrowedBookResponse usando o mapper
                .map(bookMapper::toBorrowedBookResponse)
                .toList();
        return new PageResponse<>(  // Retorna uma resposta paginada de livros devolvidos
                booksResponse,
                allBorrowedBooks.getNumber(),
                allBorrowedBooks.getSize(),
                allBorrowedBooks.getTotalElements(),
                allBorrowedBooks.getTotalPages(),
                allBorrowedBooks.isFirst(),
                allBorrowedBooks.isLast()
        );
    }

    // Método para atualizar o status de compartilhamento de um livro
    public Integer updateShareableStatus(Integer bookId, Authentication connectedUser) {
        Book book = this.bookRepository.findById(bookId)  // Busca um livro pelo ID
                .orElseThrow(() -> new EntityNotFoundException("Nenhum livro encontrado com id:: " + bookId));  // Lança uma exceção caso o livro não seja encontrado
        User user = ((User) connectedUser.getPrincipal());  // Obtém o usuário autenticado
        if (!Objects.equals(book.getOwner().getId(), user.getId())) {  // Verifica se o usuário é o proprietário do livro
            throw new OperationNotPermittedException("Você não pode atualizar o status compartilhável de outros livros");  // Lança uma exceção se o usuário não for o proprietário do livro
        }
        book.setShareable(!book.isShareable());  // Atualiza o status de compartilhamento do livro
        this.bookRepository.save(book);  // Salva as alterações no livro no banco de dados
        return bookId;  // Retorna o ID do livro
    }

    // Método para atualizar o status de arquivamento de um livro
    public Integer updateArchivedStatus(Integer bookId, Authentication connectedUser) {
        Book book = this.bookRepository.findById(bookId)  // Busca um livro pelo ID
                .orElseThrow(() -> new EntityNotFoundException("Nenhum livro encontrado com id:: " + bookId));  // Lança uma exceção caso o livro não seja encontrado
        User user = ((User) connectedUser.getPrincipal());  // Obtém o usuário autenticado
        if (!Objects.equals(book.getOwner().getId(), user.getId())) {  // Verifica se o usuário é o proprietário do livro
            throw new OperationNotPermittedException("Voce nao pode atualizar o status arquivado de outros livros.");  // Lança uma exceção se o usuário não for o proprietário do livro
        }
        book.setArchived(!book.isArchived());  // Atualiza o status de arquivamento do livro
        this.bookRepository.save(book);  // Salva as alterações no livro no banco de dados
        return bookId;  // Retorna o ID do livro
    }

//    // Método para realizar o empréstimo de um livro
    public Integer borrowBook(Integer bookId, Authentication connectedUser) {
        Book book = this.bookRepository.findById(bookId)  // Busca um livro pelo ID
                .orElseThrow(() -> new EntityNotFoundException("Nenhum livro encontrado com o id:: " + bookId));  // Lança uma exceção caso o livro não seja encontrado
        if (book.isArchived() || !book.isShareable()) {  // Verifica se o livro está arquivado ou não é compartilhável
            throw new OperationNotPermittedException("O livro solicitado não pode ser emprestado porque está arquivado ou não pode ser compartilhado");  // Lança uma exceção se o livro não puder ser emprestado
        }
        User user = ((User) connectedUser.getPrincipal());  // Obtém o usuário autenticado
        if (Objects.equals(book.getOwner().getId(), user.getId())) {  // Verifica se o usuário é o proprietário do livro
            throw new OperationNotPermittedException("Voce nao pode pegar emprestado seu proprio livro");  // Lança uma exceção se o usuário tentar emprestar seu próprio livro
        }
        final boolean isAlreadyBorrowedByUser = this.transactionHistoryRepository.isAlreadyBorrowedByUser(bookId, user.getId());  // Verifica se o usuário já pegou emprestado este livro
        if (isAlreadyBorrowedByUser) {  // Se o livro já foi pego emprestado pelo usuário
            throw new OperationNotPermittedException("Voce ja pegou este livro emprestado, e ele ainda nao foi devolvido ou a devolucao nao foi aprovada pelo proprietario");  // Lança uma exceção
        }

        final boolean isAlreadyBorrowedByOtherUser = this.transactionHistoryRepository.isAlreadyBorrowed(bookId);  // Verifica se o livro já está emprestado por outro usuário
        if (isAlreadyBorrowedByOtherUser) {  // Se o livro já está emprestado por outro usuário
            throw new OperationNotPermittedException("O livro solicitado ja esta emprestado");  // Lança uma exceção
        }

        BookTransactionHistory bookTransactionHistory = BookTransactionHistory.builder()  // Cria um novo histórico de transação de livro
                .user(user)
                .book(book)
                .returned(false)
                .returnApproved(false)
                .build();
        return this.transactionHistoryRepository.save(bookTransactionHistory).getId();  // Salva o histórico de transação no banco de dados e retorna o ID
    }

    // Método para devolver um livro emprestado
    public Integer returnBorrowedBook(Integer bookId, Authentication connectedUser) {
        Book book = this.bookRepository.findById(bookId)  // Busca um livro pelo ID
                .orElseThrow(() -> new EntityNotFoundException("Nenhum livro encontrado com o ID:: " + bookId));  // Lança uma exceção caso o livro não seja encontrado
        if (book.isArchived() || !book.isShareable()) {  // Verifica se o livro está arquivado ou não é compartilhável
            throw new OperationNotPermittedException("O livro solicitado está arquivado ou não pode ser compartilhado");  // Lança uma exceção se o livro não puder ser devolvido
        }
        User user = ((User) connectedUser.getPrincipal());  // Obtém o usuário autenticado
        if (Objects.equals(book.getOwner().getId(), user.getId())) {  // Verifica se o usuário é o proprietário do livro
            throw new OperationNotPermittedException("Você não pode emprestar ou devolver seu próprio livro");  // Lança uma exceção se o usuário tentar emprestar ou devolver seu próprio livro
        }

        BookTransactionHistory bookTransactionHistory = this.transactionHistoryRepository.findByBookIdAndUserId(bookId, user.getId())  // Busca o histórico de transação pelo ID do livro e ID do usuário
                .orElseThrow(() -> new OperationNotPermittedException("Você não pegou emprestado este livro"));  // Lança uma exceção se o usuário não tiver emprestado este livro

        bookTransactionHistory.setReturned(true);  // Define que o livro foi devolvido
        return this.transactionHistoryRepository.save(bookTransactionHistory).getId();  // Salva o histórico de transação no banco de dados e retorna o ID
    }

    // Método para aprovar a devolução de um livro emprestado
    public Integer approveReturnBorrowedBook(Integer bookId, Authentication connectedUser) {
        Book book = bookRepository.findById(bookId)  // Busca um livro pelo ID
                .orElseThrow(() -> new EntityNotFoundException("Nenhum livro encontrado com o ID:: " + bookId));  // Lança uma exceção caso o livro não seja encontrado
        if (book.isArchived() || !book.isShareable()) {  // Verifica se o livro está arquivado ou não é compartilhável
            throw new OperationNotPermittedException("O livro solicitado está arquivado ou não pode ser compartilhado");  // Lança uma exceção se o livro não puder ser aprovado para devolução
        }
        User user = ((User) connectedUser.getPrincipal());  // Obtém o usuário autenticado
        if (!Objects.equals(book.getOwner().getId(), user.getId())) {  // Verifica se o usuário é o proprietário do livro
            throw new OperationNotPermittedException("Você não pode aprovar a devolução de um livro que não lhe pertence");  // Lança uma exceção se o usuário não for o proprietário do livro
        }

        BookTransactionHistory bookTransactionHistory = transactionHistoryRepository.findByBookIdAndOwnerId(bookId, user.getId())  // Busca o histórico de transação pelo ID do livro e ID do proprietário
                .orElseThrow(() -> new OperationNotPermittedException("O livro ainda não foi devolvido. Você não pode aprovar seu retorno"));  // Lança uma exceção se o livro não tiver sido devolvido ainda

        bookTransactionHistory.setReturnApproved(true);  // Define que a devolução do livro foi aprovada
        return transactionHistoryRepository.save(bookTransactionHistory).getId();  // Salva o histórico de transação no banco de dados e retorna o ID
    }

    // Método para fazer upload da imagem de capa de um livro
    public void uploadBookCoverPicture(MultipartFile file, Authentication connectedUser, Integer bookId) {
        Book book = bookRepository.findById(bookId)  // Busca um livro pelo ID
                .orElseThrow(() -> new EntityNotFoundException("Nenhum livro encontrado com o ID:: " + bookId));  // Lança uma exceção caso o livro não seja encontrado
        User user = ((User) connectedUser.getPrincipal());  // Obtém o usuário autenticado
        var profilePicture = this.fileStorageService.saveFile(file, bookId, user.getId());  // Salva a imagem de capa do livro no serviço de armazenamento de arquivos
        book.setBookCover(profilePicture);  // Define a imagem de capa do livro
        this.bookRepository.save(book);  // Salva as alterações no livro no banco de dados
    }


}

