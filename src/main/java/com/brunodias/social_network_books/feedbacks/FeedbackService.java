package com.brunodias.social_network_books.feedbacks;

import com.brunodias.social_network_books.books.Book;
import com.brunodias.social_network_books.books.BookRepository;
import com.brunodias.social_network_books.commons.PageResponse;
import com.brunodias.social_network_books.exceptions.OperationNotPermittedException;
import com.brunodias.social_network_books.users.User;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class FeedbackService {

    // Declaração dos repositórios e do mapper usados no serviço
    private final FeedBackRepository feedBackRepository;
    private final BookRepository bookRepository;
    private final FeedbackMapper feedbackMapper;

    /**
     * Salva um feedback no sistema.
     * @param request Objeto contendo os detalhes do feedback.
     * @param connectedUser Usuário atualmente autenticado.
     * @return ID do feedback salvo.
     */
    public Integer save(FeedbackRequest request, Authentication connectedUser) {
        // Busca o livro pelo ID fornecido no request
        Book book = bookRepository.findById(request.bookId())
                .orElseThrow(() -> new EntityNotFoundException("No book found with ID:: " + request.bookId()));

        // Verifica se o livro está arquivado ou não é compartilhável
        if (book.isArchived() || !book.isShareable()) {
            throw new OperationNotPermittedException("Você não pode dar feedback sobre um livro arquivado ou não compartilhável");
        }

        // Obtém o usuário autenticado
        User user = ((User) connectedUser.getPrincipal());

        // Verifica se o usuário é o dono do livro
        if (Objects.equals(book.getOwner().getId(), user.getId())) {
            throw new OperationNotPermittedException("Você não pode dar feedback sobre seu próprio livro");
        }

        // Mapeia o request para uma entidade Feedback
        Feedback feedback = feedbackMapper.toFeedback(request);

        // Salva o feedback e retorna seu ID
        return feedBackRepository.save(feedback).getId();
    }

    /**
     * Busca todos os feedbacks de um livro específico, paginados.
     * @param bookId ID do livro.
     * @param page Número da página.
     * @param size Tamanho da página.
     * @param connectedUser Usuário atualmente autenticado.
     * @return PageResponse contendo a página de feedbacks.
     */

    /**
     * Este método é anotado com @Transactional para garantir que todas as operações
     * dentro dele sejam executadas dentro de uma transação.
     *
     * O Spring gerencia a transação automaticamente. Se qualquer exceção ocorrer
     * durante a execução do método, todas as operações realizadas dentro da
     * transação serão revertidas (rollback).
     *
     * Isso é especialmente útil para garantir a consistência dos dados no banco de dados.
     */
    @Transactional
    public PageResponse<FeedbackResponse> findAllFeedbacksByBook(Integer bookId, int page, int size, Authentication connectedUser) {
        // Cria um objeto Pageable para a paginação
        Pageable pageable = PageRequest.of(page, size);

        // Obtém o usuário autenticado
        User user = ((User) connectedUser.getPrincipal());

        // Busca os feedbacks pelo ID do livro, paginados
        Page<Feedback> feedbacks = feedBackRepository.findAllByBookId(bookId, pageable);

        // Mapeia os feedbacks para FeedbackResponse
        List<FeedbackResponse> feedbackResponses = feedbacks.stream()
                .map(f -> feedbackMapper.toFeedbackResponse(f, user.getId()))
                .toList();

        // Retorna a página de feedbacks
        return new PageResponse<>(
                feedbackResponses,
                feedbacks.getNumber(),
                feedbacks.getSize(),
                feedbacks.getTotalElements(),
                feedbacks.getTotalPages(),
                feedbacks.isFirst(),
                feedbacks.isLast()
        );
    }
}

