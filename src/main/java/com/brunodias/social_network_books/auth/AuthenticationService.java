package com.brunodias.social_network_books.auth;

import com.brunodias.social_network_books.emails.EmailService;
import com.brunodias.social_network_books.emails.EmailTemplateName;
import com.brunodias.social_network_books.role.RoleRepository;
import com.brunodias.social_network_books.security.JwtService;
import com.brunodias.social_network_books.users.Token;
import com.brunodias.social_network_books.users.TokenRepository;
import com.brunodias.social_network_books.users.User;
import com.brunodias.social_network_books.users.UserRepository;
import jakarta.mail.MessagingException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.security.SecureRandom;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;


@Service // Indica que esta classe é um serviço do Spring
@RequiredArgsConstructor // Lombok: gera um construtor com todos os campos finais (final)
public class AuthenticationService {


    // Repositório de usuários
    private final UserRepository userRepository;
    // Encoder de senhas
    private final PasswordEncoder passwordEncoder;
    // Serviço de geração de tokens JWT
    private final JwtService jwtService;
    // Gerenciador de autenticação do Spring Security
    private final AuthenticationManager authenticationManager;
    // Repositório de papéis/roles
    private final RoleRepository roleRepository;
    // Serviço de envio de emails
    private final EmailService emailService;
    // Repositório de tokens
    private final TokenRepository tokenRepository;
    // Injeção do valor da URL de ativação do arquivo de configuração
    @Value("${application.mailing.frontend.activation-url}")
    private String activationUrl;

    // Método de registro de novos usuários
    public void register(RegistrationRequest request) throws MessagingException {
        var userRole = this.roleRepository.findByName("USER")
                // TODO: Melhorar o tratamento de exceções
                .orElseThrow(() -> new IllegalStateException("ROLE USER não foi iniciado"));

        // Criação de um novo usuário
        var user = User.builder()
                .firstname(request.getFirstname())
                .lastname(request.getLastname())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .accountLocked(false)
                .enabled(false)
                .roles(List.of(userRole))
                .build();

        // Salvar o usuário no repositório
        this.userRepository.save(user);
        // Enviar email de validação
        this.sendValidationEmail(user);
    }

    public AuthenticationResponse authenticate(AuthenticationRequest request) {
        // Autentica o usuário usando o AuthenticationManager
        var auth = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(), // Email fornecido na requisição de autenticação
                        request.getPassword() // Senha fornecida na requisição de autenticação
                )
        );

        // Cria um mapa para armazenar as reivindicações do JWT
        var claims = new HashMap<String, Object>();

        // Obtém o usuário autenticado a partir do objeto de autenticação
        var user = ((User) auth.getPrincipal());

        // Adiciona o nome completo do usuário às reivindicações do JWT
        claims.put("fullName", user.getFullName());

        // Gera o token JWT com as reivindicações e o usuário autenticado
        var jwtToken = jwtService.generateToken(claims, (User) auth.getPrincipal());

        // Retorna a resposta de autenticação com o token gerado
        return AuthenticationResponse.builder()
                .token(jwtToken)
                .build();
    }

    // A anotação @Transactional garante que todo o método seja executado dentro de uma transação.
    // Se houver uma exceção não verificada, a transação será revertida.
    //@Transactional
    public void activateAccount(String token) throws MessagingException {

        Token savedToken = tokenRepository.findByToken(token)
                // todo exceção precisa ser definida
                .orElseThrow(() -> new RuntimeException("Token invalido"));
        if (LocalDateTime.now().isAfter(savedToken.getExpiresAt())) {
            sendValidationEmail(savedToken.getUser());
            throw new RuntimeException("O token de ativação expirou. Um novo token foi enviado para o mesmo endereço de e-mail");
        }

        var user = this.userRepository.findById(savedToken.getUser().getId())
                .orElseThrow(() -> new UsernameNotFoundException("Usuario nao encontrado"));
        user.setEnabled(true);
        this.userRepository.save(user);

        savedToken.setValidatedAt(LocalDateTime.now());
        this.tokenRepository.save(savedToken);

    }



    // Método para enviar email de validação
    private void sendValidationEmail(User user) throws MessagingException {
        var newToken = generateAndSaveActivationToken(user);

        this.emailService.sendEmail(
                user.getEmail(),
                user.getFullName(),
                EmailTemplateName.ACTIVATE_ACCOUNT,
                activationUrl,
                newToken,
                "Account activation"
        );
    }

    // Método para gerar e salvar um token de ativação
    private String generateAndSaveActivationToken(User user) {
        // Gerar um token
        String generatedToken = generateActivationCode(6);
        var token = Token.builder()
                .token(generatedToken)
                .createdAt(LocalDateTime.now())
                .expiresAt(LocalDateTime.now().plusMinutes(15))
                .user(user)
                .build();
        tokenRepository.save(token);

        return generatedToken;
    }

    // Método para gerar um código de ativação aleatório
    private String generateActivationCode(int length) {
        String characters = "0123456789";  // Conjunto de caracteres que serão usados para gerar o código
        StringBuilder codeBuilder = new StringBuilder();  // StringBuilder para construir o código

        SecureRandom secureRandom = new SecureRandom();  // Instância de SecureRandom para garantir a aleatoriedade

        for (int i = 0; i < length; i++) {  // Loop para gerar cada caractere do código
            int randomIndex = secureRandom.nextInt(characters.length());  // Gera um índice aleatório dentro do intervalo do conjunto de caracteres
            codeBuilder.append(characters.charAt(randomIndex));  // Adiciona o caractere aleatório ao código
        }

        return codeBuilder.toString();  // Retorna o código gerado como uma string
    }

}
