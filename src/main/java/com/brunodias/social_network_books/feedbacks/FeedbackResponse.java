package com.brunodias.social_network_books.feedbacks;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

// Gera automaticamente os métodos getter para todos os campos.
@Getter
// Gera automaticamente os métodos setter para todos os campos.
@Setter
// Gera automaticamente um construtor com um argumento para cada campo da classe.
@AllArgsConstructor
// Gera automaticamente um construtor sem argumentos.
@NoArgsConstructor
// Gera automaticamente o padrão de projeto Builder para a classe.
@Builder
public class FeedbackResponse {

    // Campo que armazena a nota do feedback.
    private Double note;

    // Campo que armazena o comentário do feedback.
    private String comment;

    // Campo booleano que indica se o feedback pertence ao usuário atual.
    private boolean ownFeedback;
}
