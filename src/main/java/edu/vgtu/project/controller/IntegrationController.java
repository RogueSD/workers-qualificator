package edu.vgtu.project.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.FileInputStream;
import java.nio.charset.StandardCharsets;

@Slf4j
@RestController
@RequestMapping("/integration")
public class IntegrationController {
    @GetMapping("/export")
    public ResponseEntity<Resource> export() {
        log.info("Получен запрос экспорта файла для системы 1С.");
        ByteArrayResource resource = new ByteArrayResource("test".getBytes(StandardCharsets.UTF_8));
        log.info("Возрващение заглушки");
        return ResponseEntity.ok()
                .contentLength(resource.contentLength())
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=test.txt")
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .body(resource);
    }
}
