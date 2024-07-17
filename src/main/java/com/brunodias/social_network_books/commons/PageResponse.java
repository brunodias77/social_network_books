package com.brunodias.social_network_books.commons;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

// @Getter: Gera automaticamente os métodos getters para todos os campos da classe.
@Getter
// @Setter: Gera automaticamente os métodos setters para todos os campos da classe.
@Setter
// @Builder: Implementa o padrão de projeto Builder para a classe. Facilita a construção de objetos desta classe.
@Builder
// @AllArgsConstructor: Gera automaticamente um construtor com um parâmetro para cada campo na classe.
@AllArgsConstructor
// @NoArgsConstructor: Gera automaticamente um construtor sem parâmetros.
@NoArgsConstructor
public class PageResponse<T> {
    // Lista de conteúdo da página.
    private List<T> content;
    // Número da página atual.
    private int number;
    // Tamanho da página (número de elementos).
    private int size;
    // Total de elementos disponíveis.
    private long totalElements;
    // Total de páginas disponíveis.
    private int totalPages;
    // Indica se esta é a primeira página.
    private boolean first;
    // Indica se esta é a última página.
    private boolean last;
}

