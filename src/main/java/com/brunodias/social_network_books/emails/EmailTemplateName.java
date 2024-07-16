package com.brunodias.social_network_books.emails;
import lombok.Getter;

@Getter // Gera os métodos getter para os campos desta classe
public enum EmailTemplateName {

    ACTIVATE_ACCOUNT("activate_account"); // Define um valor do enum com o nome "activate_account"

    private final String name; // Campo para armazenar o nome do template

    // Construtor do enum que inicializa o campo name
    EmailTemplateName(String name) {
        this.name = name;
    }
}