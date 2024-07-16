package com.brunodias.social_network_books.auth;

import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.mail.MessagingException;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController // Indica que esta classe é um controlador REST
@RequestMapping("auth") // Mapeia todas as URLs iniciadas com "/auth" para este controlador
@RequiredArgsConstructor // Gera um construtor com argumentos para todos os campos final na classe
@Tag(name = "Authentication") // Anotação do Swagger para descrever a tag de Swagger para este controlador
public class AuthenticationController {

    private final AuthenticationService authenticationService;
    @PostMapping("/register")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public ResponseEntity<?> register(@RequestBody @Valid RegistrationRequest request) throws MessagingException {
        this.authenticationService.register(request);
        return ResponseEntity.accepted().build();
    }

    @PostMapping("/authenticate")
    public ResponseEntity<AuthenticationResponse> authenticate(
            @RequestBody AuthenticationRequest request) {
        return ResponseEntity.ok(this.authenticationService.authenticate(request));
    }

    @GetMapping("/activate-account")
    public void confirm( @RequestParam String token) throws MessagingException {
        this.authenticationService.activateAccount(token);
    }


}
