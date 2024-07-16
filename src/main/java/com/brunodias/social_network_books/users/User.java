package com.brunodias.social_network_books.users;


import com.brunodias.social_network_books.books.Book;
import com.brunodias.social_network_books.histories.BookTransactionHistory;
import com.brunodias.social_network_books.role.Role;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.security.Principal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import static jakarta.persistence.FetchType.EAGER;

@Getter // Anotação do Lombok para gerar métodos getters para todos os campos
@Setter // Anotação do Lombok para gerar métodos setters para todos os campos
@SuperBuilder // Anotação do Lombok para implementar o padrão Builder com suporte a herança
@NoArgsConstructor // Anotação do Lombok para gerar um construtor sem argumentos
@AllArgsConstructor // Anotação do Lombok para gerar um construtor com todos os argumentos
@Entity // Anotação do JPA que indica que esta classe é uma entidade
@Table(name = "_user") // Especifica o nome da tabela no banco de dados
@EntityListeners(AuditingEntityListener.class) // Habilita a auditoria automática de criação e modificação de entidades
public class User implements UserDetails, Principal {

    @Id
    @GeneratedValue // Gera automaticamente o valor do identificador
    private Integer id;
    private String firstname;
    private String lastname;
    private LocalDate dateOfBirth;
    @Column(unique = true) // Garante que o valor da coluna seja único no banco de dados
    private String email;
    private String password;
    private boolean accountLocked;
    private boolean enabled;

    @ManyToMany(fetch = EAGER) // Relacionamento muitos-para-muitos, carregado de forma ansiosa
    private List<Role> roles;

    @OneToMany(mappedBy = "owner") // Relacionamento um-para-muitos, mapeado pelo campo "owner" na entidade Book
    private List<Book> books;

    @OneToMany(mappedBy = "user") // Relacionamento um-para-muitos, mapeado pelo campo "user" na entidade BookTransactionHistory
    private List<BookTransactionHistory> histories;

    @CreatedDate // Anotação do Spring Data para marcar a data de criação da entidade
    @Column(nullable = false, updatable = false) // Garante que a coluna não seja nula e não possa ser atualizada
    private LocalDateTime createdDate;

    @LastModifiedDate // Anotação do Spring Data para marcar a data da última modificação da entidade
    @Column(insertable = false) // Garante que a coluna não possa ser inserida diretamente
    private LocalDateTime lastModifiedDate;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        // Retorna as autoridades concedidas ao usuário (roles convertidas para GrantedAuthority)
        return this.roles
                .stream()
                .map(r -> new SimpleGrantedAuthority(r.getName()))
                .collect(Collectors.toList());
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return !accountLocked;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return enabled;
    }

    public String fullName() {
        return getFirstname() + " " + getLastname();
    }

    @Override
    public String getName() {
        return email;
    }

    public String getFullName() {
        return firstname + " " + lastname;
    }
}


