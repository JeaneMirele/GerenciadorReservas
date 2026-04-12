package com.pa1.sistema_gerenciador_reserva.controllers;

import com.pa1.sistema_gerenciador_reserva.services.FileStorageService;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/arquivos")
@RequiredArgsConstructor
public class ArquivoController {

    private final FileStorageService fileStorageService;

    @GetMapping("/{nomeArquivo:.+}")
    public ResponseEntity<Resource> servirArquivo(@PathVariable String nomeArquivo) {
        Resource resource = fileStorageService.carregarArquivo(nomeArquivo);

        String contentType = "image/jpeg";
        String nome = nomeArquivo.toLowerCase();
        if (nome.endsWith(".png")) contentType = "image/png";
        else if (nome.endsWith(".gif")) contentType = "image/gif";
        else if (nome.endsWith(".webp")) contentType = "image/webp";

        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(contentType))
                .header(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=\"" + nomeArquivo + "\"")
                .body(resource);
    }
}
