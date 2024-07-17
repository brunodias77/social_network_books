package com.brunodias.social_network_books.feedbacks;

import com.brunodias.social_network_books.commons.PageResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

// Indica que esta classe é um controlador REST, permitindo a definição de endpoints RESTful.
@RestController
// Define o mapeamento de URL base para os endpoints neste controlador.
// Todos os endpoints definidos nesta classe estarão sob "/feedbacks".
@RequestMapping("feedbacks")
// Gera um construtor que inicializa todos os campos finais (final) ou marcados com @NonNull.
// Útil para injeção de dependências via construtor.
@RequiredArgsConstructor
// Anotação usada pelo Swagger/OpenAPI para documentar a API.
// Define uma tag para agrupar endpoints relacionados sob o nome "Feedback" na documentação gerada.
@Tag(name = "Feedback")
public class FeedbackController {

    // Injeção de dependência do serviço de feedback.
    private final FeedbackService feedbackService;

    // Define um endpoint para salvar feedback.
    // O método aceita um objeto FeedbackRequest validado e o usuário autenticado como parâmetros.
    @PostMapping
    public ResponseEntity<Integer> saveFeedback(
            @Valid @RequestBody FeedbackRequest request,
            Authentication connectedUser
    ) {
        // Chama o serviço para salvar o feedback e retorna a resposta.
        return ResponseEntity.ok(this.feedbackService.save(request, connectedUser));
    }

    // Define um endpoint para obter todos os feedbacks de um livro específico.
    // O método aceita o ID do livro, os parâmetros de paginação (page e size), e o usuário autenticado como parâmetros.
    @GetMapping("/book/{book-id}")
    public ResponseEntity<PageResponse<FeedbackResponse>> findAllFeedbacksByBook(
            @PathVariable("book-id") Integer bookId,
            @RequestParam(name = "page", defaultValue = "0", required = false) int page,
            @RequestParam(name = "size", defaultValue = "10", required = false) int size,
            Authentication connectedUser
    ) {
        // Chama o serviço para obter os feedbacks do livro e retorna a resposta.
        return ResponseEntity.ok(this.feedbackService.findAllFeedbacksByBook(bookId, page, size, connectedUser));
    }
}

