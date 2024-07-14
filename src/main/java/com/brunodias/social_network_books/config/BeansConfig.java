package com.brunodias.social_network_books.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

import java.util.Arrays;
import java.util.Collections;

@Configuration // Indica que esta classe é uma configuração do Spring
@RequiredArgsConstructor // Gera um construtor com argumentos para todos os campos final na classe
public class BeansConfig {

    private final UserDetailsService userDetailsService; // Serviço para carregar detalhes do usuário

    @Bean // Indica que este método retorna um bean gerenciado pelo Spring
    public AuthenticationProvider authenticationProvider() {
        // Configura um provedor de autenticação que utiliza o serviço de detalhes do usuário e um encoder de senha
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userDetailsService);
        authProvider.setPasswordEncoder(passwordEncoder());
        return authProvider;
    }

    @Bean // Indica que este método retorna um bean gerenciado pelo Spring
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        // Obtém o gerenciador de autenticação do Spring Security
        return config.getAuthenticationManager();
    }

    @Bean // Indica que este método retorna um bean gerenciado pelo Spring
    public PasswordEncoder passwordEncoder() {
        // Retorna um encoder de senha BCrypt para criptografar senhas
        return new BCryptPasswordEncoder();
    }

    @Bean // Indica que este método retorna um bean gerenciado pelo Spring
    public AuditorAware<Integer> auditorAware() {
        // Retorna um provedor de auditoria personalizado
        return new ApplicationAuditAware();
    }

    @Bean // Indica que este método retorna um bean gerenciado pelo Spring
    public CorsFilter corsFilter() {
        // Configura o filtro CORS para permitir requisições da origem especificada
        final UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        final CorsConfiguration config = new CorsConfiguration();
        config.setAllowCredentials(true);
        config.setAllowedOrigins(Collections.singletonList("http://localhost:4200")); // Permite origens específicas
        config.setAllowedHeaders(Arrays.asList(
                HttpHeaders.ORIGIN,
                HttpHeaders.CONTENT_TYPE,
                HttpHeaders.ACCEPT,
                HttpHeaders.AUTHORIZATION
        ));
        config.setAllowedMethods(Arrays.asList(
                "GET",
                "POST",
                "DELETE",
                "PUT",
                "PATCH"
        ));
        source.registerCorsConfiguration("/**", config); // Registra a configuração CORS para todos os caminhos
        return new CorsFilter(source); // Retorna o filtro CORS configurado
    }

}


