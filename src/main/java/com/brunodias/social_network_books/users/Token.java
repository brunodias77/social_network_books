package com.brunodias.social_network_books.users;

import jakarta.persistence.Entity;
import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter // Gera automaticamente os métodos getter para todos os campos
@Setter // Gera automaticamente os métodos setter para todos os campos
@Builder // Gera um builder para a classe, permitindo uma construção mais flexível dos objetos
@AllArgsConstructor // Gera um construtor com um argumento para cada campo na classe
@NoArgsConstructor // Gera um construtor sem argumentos
@Entity // Marca esta classe como uma entidade JPA
public class Token {

    @Id
    @GeneratedValue // Indica que o valor do campo id será gerado automaticamente
    private Integer id;

    @Column(unique = true) // Define que o valor deste campo deve ser único no banco de dados
    private String token;

    private LocalDateTime createdAt; // Data/hora de criação do token
    private LocalDateTime expiresAt; // Data/hora de expiração do token
    private LocalDateTime validatedAt; // Data/hora de validação do token

    @ManyToOne // Define um relacionamento muitos-para-um com a entidade User
    @JoinColumn(name = "userId", nullable = false) // Define a coluna de junção no banco de dados e especifica que não pode ser nula
    private User user; // Usuário associado ao token
}
