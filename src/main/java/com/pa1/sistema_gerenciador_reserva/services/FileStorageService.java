package com.pa1.sistema_gerenciador_reserva.services;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

@Service
public class FileStorageService {

    @Value("${UPLOAD_DIR:/app/uploads}")
    private String diretorioUpload;


    public String salvarArquivo(MultipartFile arquivo, String prefixo, String id) {
        Path pathRaiz = Paths.get(diretorioUpload).toAbsolutePath().normalize();
        try {
            Files.createDirectories(pathRaiz);

            String nomeArquivo = prefixo + "_" + id + "_" + System.currentTimeMillis() + getExtensao(arquivo.getOriginalFilename());
            Path destino = pathRaiz.resolve(nomeArquivo);

            Files.copy(arquivo.getInputStream(), destino, StandardCopyOption.REPLACE_EXISTING);

            return nomeArquivo;
        } catch (IOException e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Erro ao armazenar arquivo.");
        }
    }


    public void deletarArquivo(String nomeArquivo) {
        if (nomeArquivo == null || nomeArquivo.isBlank()) return;

        try {
            Path caminho = Paths.get(diretorioUpload).toAbsolutePath().normalize().resolve(nomeArquivo);
            Files.deleteIfExists(caminho);
        } catch (IOException e) {

        }
    }


    public Resource carregarArquivo(String nomeArquivo) {
        try {
            Path caminho = Paths.get(diretorioUpload).toAbsolutePath().normalize().resolve(nomeArquivo).normalize();
            Resource resource = new UrlResource(caminho.toUri());

            if (resource.exists() && resource.isReadable()) {
                return resource;
            } else {
                throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Imagem não encontrada.");
            }
        } catch (MalformedURLException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Erro ao localizar o arquivo.");
        }
    }

    private String getExtensao(String nome) {
        if (nome == null || !nome.contains(".")) return ".jpg";
        return nome.substring(nome.lastIndexOf("."));
    }
}
