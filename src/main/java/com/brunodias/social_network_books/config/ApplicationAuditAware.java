package com.brunodias.social_network_books.config;

import com.brunodias.social_network_books.users.User;
import org.springframework.data.domain.AuditorAware;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Optional;

// Implementa a interface AuditorAware<T> do Spring Data para fornecer o auditor atual
public class ApplicationAuditAware implements AuditorAware<Integer> {
    // Método para obter o auditor atual (ID do usuário)
    @Override
    public Optional<Integer> getCurrentAuditor() {
        // Obtém o contexto de segurança do Spring para acessar informações de autenticação
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        // Verifica se não há autenticação, se não está autenticado ou se é uma autenticação anônima
        if (authentication == null ||
                !authentication.isAuthenticated() ||
                authentication instanceof AnonymousAuthenticationToken) {
            return Optional.empty(); // Retorna Optional vazio se não houver auditor válido
        }

        // Obtém o usuário autenticado (principal)
        User userPrincipal = (User) authentication.getPrincipal();

        // Retorna o ID do usuário como auditor
        return Optional.ofNullable(userPrincipal.getId());
    }
}
