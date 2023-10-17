package edu.vgtu.project.controller;

import edu.vgtu.project.dto.WorkerDto;
import edu.vgtu.project.service.WorkerService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/worker")
@RequiredArgsConstructor
public class WorkerController {
     private final WorkerService service;

     @GetMapping(value = "/get/{id}")
     public WorkerDto getWorkerById(@PathVariable Long id) {
         log.info("Получен запрос на получение данных работника с идентификатором: {}", id);

         final WorkerDto worker = service.get(id);

         log.info("Сформированные данные работника: {}", worker);

         return worker;
     }
}
