package com.brunodias.social_network_books.emails;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring6.SpringTemplateEngine;

import java.util.HashMap;
import java.util.Map;

import static java.nio.charset.StandardCharsets.UTF_8;
import static org.springframework.mail.javamail.MimeMessageHelper.MULTIPART_MODE_MIXED;

@Service // Indica que esta classe é um serviço gerido pelo Spring
@Slf4j // Gera um logger para a classe
@RequiredArgsConstructor // Gera um construtor com todos os campos finais (final)
public class EmailService {
    private final JavaMailSender mailSender; // Objeto responsável por enviar e-mails
    private final SpringTemplateEngine templateEngine; // Motor de templates para processar e-mails

    @Async // Indica que este método deve ser executado de forma assíncrona
    public void sendEmail(
            String to, // Endereço de e-mail do destinatário
            String username, // Nome de usuário
            EmailTemplateName emailTemplate, // Nome do template de e-mail
            String confirmationUrl, // URL de confirmação
            String activationCode, // Código de ativação
            String subject // Assunto do e-mail
    ) throws MessagingException {

        String templateName;
        if (emailTemplate == null) {
            templateName = "confirm-email"; // Nome padrão do template
        } else {
            templateName = emailTemplate.name(); // Nome do template especificado
        }

        // Cria uma nova mensagem MIME
        MimeMessage mimeMessage = mailSender.createMimeMessage();

        // Configura o helper para construir a mensagem
        MimeMessageHelper helper = new MimeMessageHelper(
                mimeMessage,
                MULTIPART_MODE_MIXED, // Permite múltiplas partes no e-mail
                UTF_8.name() // Define a codificação de caracteres como UTF-8
        );

        // Cria um mapa de propriedades a serem usadas no template
        Map<String, Object> properties = new HashMap<>();
        properties.put("username", username);
        properties.put("confirmationUrl", confirmationUrl);
        properties.put("activation_code", activationCode);

        // Cria o contexto do Thymeleaf e define as variáveis
        Context context = new Context();
        context.setVariables(properties);

        helper.setFrom("contact@brunodias.com.br"); // Define o remetente do e-mail
        helper.setTo(to); // Define o destinatário do e-mail
        helper.setSubject(subject); // Define o assunto do e-mail

        // Processa o template e gera o conteúdo do e-mail
        String template = templateEngine.process(templateName, context);

        helper.setText(template, true); // Define o conteúdo do e-mail como HTML

        // Envia o e-mail
        mailSender.send(mimeMessage);
    }
}

