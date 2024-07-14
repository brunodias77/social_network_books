package com.brunodias.social_network_books.security;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import static org.springframework.security.config.Customizer.withDefaults;
import static org.springframework.security.config.http.SessionCreationPolicy.STATELESS;

@Configuration // Indica que esta classe é uma configuração do Spring
@EnableWebSecurity // Habilita a segurança baseada em web do Spring Security
@RequiredArgsConstructor // Gera um construtor com argumentos para todos os campos final na classe
@EnableMethodSecurity(securedEnabled = true) // Habilita a segurança baseada em método com a anotação @Secured

public class SecurityConfig {

    private final JwtFilter jwtAuthFilter; // Filtro JWT personalizado para autenticação via token JWT
    private final AuthenticationProvider authenticationProvider; // Provedor de autenticação personalizado

    @Bean // Indica que este método retorna um bean gerenciado pelo Spring
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .cors(withDefaults()) // Configura o CORS com as configurações padrão do Spring Security
                .csrf(AbstractHttpConfigurer::disable) // Desabilita a proteção CSRF
                .authorizeHttpRequests(req ->
                        req.requestMatchers( // Configura as URLs que devem ser autorizadas sem autenticação
                                        "/auth/**",
                                        "/v2/api-docs",
                                        "/v3/api-docs",
                                        "/v3/api-docs/**",
                                        "/swagger-resources",
                                        "/swagger-resources/**",
                                        "/configuration/ui",
                                        "/configuration/security",
                                        "/swagger-ui/**",
                                        "/webjars/**",
                                        "/swagger-ui.html"
                                ).permitAll() // Permite acesso sem autenticação às URLs especificadas
                                .anyRequest().authenticated() // Exige autenticação para qualquer outra requisição não listada acima
                )
                .sessionManagement(session ->
                        session.sessionCreationPolicy(STATELESS)) // Configura a política de gerenciamento de sessão como stateless (sem estado)
                .authenticationProvider(authenticationProvider) // Define o provedor de autenticação personalizado
                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class); // Adiciona o filtro JWT antes do filtro padrão de autenticação de usuário e senha

        return http.build(); // Constrói e retorna a cadeia de filtros de segurança configurada
    }
}

