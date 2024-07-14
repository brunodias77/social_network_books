package com.brunodias.social_network_books.role;

import com.brunodias.social_network_books.users.User;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;
import java.util.List;

@Getter // Gera automaticamente os métodos getter para todos os campos
@Setter // Gera automaticamente os métodos setter para todos os campos
@Builder // Gera um builder para a classe, permitindo uma construção mais flexível dos objetos
@NoArgsConstructor // Gera um construtor sem argumentos
@AllArgsConstructor // Gera um construtor com um argumento para cada campo na classe
@Entity // Marca esta classe como uma entidade JPA
@Table(name = "role") // Especifica o nome da tabela no banco de dados que será usada para mapear esta entidade
@EntityListeners(AuditingEntityListener.class) // Adiciona um listener para auditoria, para preencher automaticamente as datas de criação e modificação
public class Role {

    @Id
    @GeneratedValue // Indica que o valor do campo id será gerado automaticamente pelo provedor de persistência
    private Integer id;

    @Column(unique = true) // Define que o valor deste campo deve ser único no banco de dados
    private String name;

    @ManyToMany(mappedBy = "roles")  // Define um relacionamento muitos-para-muitos com a entidade User. O atributo 'mappedBy' indica que a associação é bidirecional e que a tabela de junção (join table) é gerenciada pela entidade User através do campo 'roles'
    @JsonIgnore // Ignora este campo durante a serialização/deserialização JSON
    private List<User> user;

    @CreatedDate // Marca o campo para armazenar a data/hora de criação da entidade
    @Column(nullable = false, updatable = false) // Define que este campo não pode ser nulo e não pode ser atualizado após a inserção
    private LocalDateTime createdDate;

    @LastModifiedDate // Marca o campo para armazenar a data/hora da última modificação da entidade
    @Column(insertable = false) // Define que este campo não será considerado durante a inserção da entidade
    private LocalDateTime lastModifiedDate;
}

