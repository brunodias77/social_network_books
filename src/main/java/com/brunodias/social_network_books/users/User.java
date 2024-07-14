package com.brunodias.social_network_books.users;

import com.brunodias.social_network_books.role.Role;
import jakarta.persistence.*;
import lombok.*;
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

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "_user")
@EntityListeners(AuditingEntityListener.class)
public class User implements UserDetails, Principal {

    @Id
    @GeneratedValue
    private Integer id;
    private String firstname;
    private String lastname;
    private LocalDate dateOfBirth;
    @Column(unique = true)
    private String email;
    private String password;
    private boolean accountLocked;
    private boolean enable;
    @ManyToMany(fetch = EAGER)
    private List<Role> roles;
//    @OneToMany(mappedBy = "owner")
//    private List<Book> books;
//    @OneToMany(mappedBy = "user")
//    private List<BookTransactionHistory> histories;

    @CreatedDate
    @Column(nullable = false, updatable = false)
    private LocalDateTime createdDate;
    @LastModifiedDate
    @Column(insertable = false)
    private LocalDateTime lastModifieDate;
    @Override
    public String getName() {
        return email;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        // Retorna uma coleção de GrantedAuthority baseada nos papéis (roles) do usuário
        return this.roles.stream()
                .map((r) -> new SimpleGrantedAuthority(r.getName())) // Mapeia cada Role para um SimpleGrantedAuthority com o nome da Role
                .collect(Collectors.toList()); // Coleta as SimpleGrantedAuthority em uma lista
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
        return enable;
    }

    private String fullName(){
        return firstname + " " + lastname;
    }
}


//@Getter // Gera automaticamente os métodos getter para todos os campos
//@Setter // Gera automaticamente os métodos setter para todos os campos
//@Builder // Gera um builder para a classe, permitindo uma construção mais flexível dos objetos
//@AllArgsConstructor // Gera um construtor com um argumento para cada campo na classe
//@NoArgsConstructor // Gera um construtor sem argumentos
//@Entity // Marca esta classe como uma entidade JPA
//@Table(name = "_user") // Especifica o nome da tabela no banco de dados para esta entidade
//@EntityListeners(AuditingEntityListener.class) // Adiciona um listener para preencher automaticamente createdDate e lastModifiedDate
//public class User implements UserDetails, Principal {
//
//    @Id
//    @GeneratedValue // Indica que o valor do campo id será gerado automaticamente
//    private Integer id;
//    private String firstname;
//    private String lastname;
//    private LocalDate dateOfBirth;
//
//    @Column(unique = true) // Define que o valor deste campo deve ser único no banco de dados
//    private String email;
//    private String password;
//    private boolean accountLocked;
//    private boolean enable;
//
//    @ManyToMany(fetch = EAGER) // Define um relacionamento muitos-para-muitos com a entidade Role e especifica o tipo de carregamento como EAGER
//    private List<Role> roles;
//
//    @OneToMany(mappedBy = "owner") // Define um relacionamento um-para-muitos com a entidade Book e especifica o campo proprietário na entidade Book
//    private List<Book> books;
//
//    @OneToMany(mappedBy = "user") // Define um relacionamento um-para-muitos com a entidade BookTransactionHistory e especifica o campo usuário na entidade BookTransactionHistory
//    private List<BookTransactionHistory> histories;
//
//    @CreatedDate // Marca o campo para armazenar a data/hora de criação da entidade
//    @Column(nullable = false, updatable = false) // Define que o campo não pode ser nulo e não pode ser atualizado após a criação
//    private LocalDateTime createdDate;
//
//    @LastModifiedDate // Marca o campo para armazenar a data/hora da última modificação da entidade
//    @Column(insertable = false) // Define que este campo não será considerado durante a inserção da entidade
//    private LocalDateTime lastModifieDate;
//
//    @Override
//    public String getName() {
//        return email;
//    }
//
//    @Override
//    public Collection<? extends GrantedAuthority> getAuthorities() {
//        return null;
//    }
//
//    @Override
//    public String getPassword() {
//        return password;
//    }
//
//    @Override
//    public String getUsername() {
//        return email;
//    }
//
//    @Override
//    public boolean isAccountNonExpired() {
//        return true;
//    }
//
//    @Override
//    public boolean isAccountNonLocked() {
//        return !accountLocked;
//    }
//
//    @Override
//    public boolean isCredentialsNonExpired() {
//        return true;
//    }
//
//    @Override
//    public boolean isEnabled() {
//        return enable;
//    }
//
//    private String fullName() {
//        return firstname + " " + lastname;
//    }
//}
