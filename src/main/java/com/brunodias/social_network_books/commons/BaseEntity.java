package com.brunodias.social_network_books.commons;

import jakarta.persistence.Column;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.MappedSuperclass;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Getter
@Setter
@SuperBuilder  // Lombok: Gera um padrão Builder para a classe
@AllArgsConstructor  // Lombok: Gera um construtor com todos os argumentos
@NoArgsConstructor  // Lombok: Gera um construtor sem argumentos
@MappedSuperclass  // JPA: Indica que esta classe não é uma entidade por si só, mas suas informações de mapeamento serão aplicadas às entidades que a estendem
@EntityListeners(AuditingEntityListener.class)  // JPA: Indica que esta classe escuta eventos de ciclo de vida da entidade, como criação e modificação
public class BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)  // JPA: Gera automaticamente o valor da chave primária
    private Integer id;

    @CreatedDate  // Spring Data JPA: Marca o campo com a data de criação da entidade
    @Column(nullable = false, updatable = false)  // JPA: Mapeia o campo para uma coluna no banco de dados, não permitindo nulo e não atualizável
    private LocalDateTime createdDate;

    @LastModifiedDate  // Spring Data JPA: Marca o campo com a data da última modificação da entidade
    @Column(insertable = false)  // JPA: Mapeia o campo para uma coluna no banco de dados, apenas para inserção, não atualizável
    private LocalDateTime lastModifiedDate;

    @CreatedBy  // Spring Data JPA: Marca o campo com o ID do usuário que criou a entidade
    @Column(nullable = false, updatable = false)  // JPA: Mapeia o campo para uma coluna no banco de dados, não permitindo nulo e não atualizável
    private Integer createdBy;

    @LastModifiedBy  // Spring Data JPA: Marca o campo com o ID do usuário que modificou pela última vez a entidade
    @Column(insertable = false)  // JPA: Mapeia o campo para uma coluna no banco de dados, apenas para inserção, não atualizável
    private Integer lastModifiedBy;
}

