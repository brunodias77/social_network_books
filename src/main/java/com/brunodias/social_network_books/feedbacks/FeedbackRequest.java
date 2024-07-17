package com.brunodias.social_network_books.feedbacks;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

// A classe FeedbackRequest é um record que representa uma solicitação de feedback.
public record FeedbackRequest(
        // A nota deve ser um número positivo.
        @Positive(message = "200")
        // A nota deve ser no mínimo 0.
        @Min(value = 0, message = "201")
        // A nota deve ser no máximo 5.
        @Max(value = 5, message = "202")
        Double note,

        // O comentário não pode ser nulo.
        @NotNull(message = "203")
        // O comentário não pode estar vazio.
        @NotEmpty(message = "203")
        // O comentário não pode estar em branco.
        @NotBlank(message = "203")
        String comment,

        // O ID do livro não pode ser nulo.
        @NotNull(message = "204")
        Integer bookId
) {
}

