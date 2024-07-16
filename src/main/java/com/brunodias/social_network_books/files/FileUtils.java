package com.brunodias.social_network_books.files;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

@Slf4j
public class FileUtils {

    // Método estático para ler o conteúdo de um arquivo a partir de sua localização
    public static byte[] readFileFromLocation(String fileUrl) {
        if (StringUtils.isBlank(fileUrl)) { // Verifica se a URL do arquivo é vazia ou nula
            return null;
        }
        try {
            Path filePath = new File(fileUrl).toPath(); // Converte a URL do arquivo em um caminho Path
            return Files.readAllBytes(filePath); // Lê todos os bytes do arquivo e retorna como array de bytes
        } catch (IOException e) {
            log.warn("Nenhum arquivo encontrado no caminho {}", fileUrl); // Registra um aviso (warning) no log caso ocorra uma exceção de E/S
        }
        return null; // Retorna nulo se houver problemas ao ler o arquivo
    }
}

