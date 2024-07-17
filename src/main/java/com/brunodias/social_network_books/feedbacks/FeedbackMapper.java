package com.brunodias.social_network_books.feedbacks;

import com.brunodias.social_network_books.books.Book;
import org.springframework.stereotype.Service;

import java.util.Objects;

// Indica que esta classe é um componente Spring gerenciado como um serviço.
// Serviços geralmente contêm a lógica de negócios da aplicação.
@Service
public class FeedbackMapper {

    // Método para mapear FeedbackRequest para Feedback.
    public Feedback toFeedback(FeedbackRequest request) {
        // Cria um objeto Feedback a partir dos dados do FeedbackRequest.
        return Feedback.builder()
                .note(request.note())
                .comment(request.comment())
                .book(Book.builder()
                        .id(request.bookId())
                        .shareable(false) // Não necessário e sem impacto :: apenas para satisfazer o Lombok
                        .archived(false) // Não necessário e sem impacto :: apenas para satisfazer o Lombok
                        .build()
                )
                .build();
    }

    // Método para mapear Feedback para FeedbackResponse.
    public FeedbackResponse toFeedbackResponse(Feedback feedback, Integer id) {
        // Cria um objeto FeedbackResponse a partir dos dados do Feedback.
        return FeedbackResponse.builder()
                .note(feedback.getNote())
                .comment(feedback.getComment())
                .ownFeedback(Objects.equals(feedback.getCreatedBy(), id)) // Verifica se o feedback pertence ao usuário atual
                .build();
    }
}

