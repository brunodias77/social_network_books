package com.brunodias.social_network_books.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component // Indica que esta classe é um componente gerenciado pelo Spring
@RequiredArgsConstructor // Gera um construtor com argumentos para todos os campos final na classe
public class JwtFilter extends OncePerRequestFilter {

    private final JwtService jwtService; // Serviço para manipulação de tokens JWT
    private final UserDetailsService userDetailsService; // Serviço para carregar detalhes do usuário

    @Override
    protected void doFilterInternal(
            @NonNull HttpServletRequest request, // @NonNull é usada para indicar que um parâmetro de método não deve ser nulo durante a execução do método
            @NonNull HttpServletResponse response,
            @NonNull FilterChain filterChain
    ) throws ServletException, IOException {

        // Verifica se a requisição está relacionada ao endpoint de autenticação e permite o acesso sem filtro JWT
        if (request.getServletPath().contains("/api/v1/auth")) {
            filterChain.doFilter(request, response);
            return;
        }

        // Obtém o cabeçalho Authorization da requisição
        final String authHeader = request.getHeader("Authorization");
        final String jwt;
        final String userEmail;

        // Verifica se o cabeçalho de autorização é válido
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            filterChain.doFilter(request, response);
            return;
        }

        // Extrai o token JWT do cabeçalho
        jwt = authHeader.substring(7);
        userEmail = jwtService.extractUsername(jwt);

        // Verifica se o token JWT é válido e configura a autenticação no contexto de segurança do Spring
        if (userEmail != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            UserDetails userDetails = this.userDetailsService.loadUserByUsername(userEmail);
            if (jwtService.isTokenValid(jwt, userDetails)) {
                UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                        userDetails,
                        null,
                        userDetails.getAuthorities()
                );
                authToken.setDetails(
                        new WebAuthenticationDetailsSource().buildDetails(request)
                );
                SecurityContextHolder.getContext().setAuthentication(authToken);
            }
        }

        // Continua o filtro da cadeia com a requisição e resposta atuais
        filterChain.doFilter(request, response);
    }
}
