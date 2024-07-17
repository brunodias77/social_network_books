package com.brunodias.social_network_books.exceptions;

/**
 * Exceção personalizada para indicar que uma operação não é permitida.
 * Esta exceção estende RuntimeException, o que a torna uma exceção não verificada.
 */
public class OperationNotPermittedException extends RuntimeException {

    /**
     * Construtor padrão da exceção.
     * Cria uma nova instância de OperationNotPermittedException sem mensagem detalhada.
     */
    public OperationNotPermittedException() {
    }

    /**
     * Construtor que aceita uma mensagem de erro detalhada.
     * @param message A mensagem detalhada indicando a razão da exceção.
     */
    public OperationNotPermittedException(String message) {
        super(message); // Chama o construtor da superclasse (RuntimeException) com a mensagem fornecida.
    }
}
