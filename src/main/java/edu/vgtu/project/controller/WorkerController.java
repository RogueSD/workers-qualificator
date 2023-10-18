package edu.vgtu.project.controller;

import edu.vgtu.project.dto.QualificationDto;
import edu.vgtu.project.dto.SpecializationDto;
import edu.vgtu.project.dto.WorkerDto;
import edu.vgtu.project.service.WorkerService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/worker")
@RequiredArgsConstructor
public class WorkerController {
     private final WorkerService workerService;

     @GetMapping(value = "/{id}")
     public WorkerDto getWorkerById(@PathVariable Long id) {
         log.info("Получен запрос на получение данных работника с идентификатором: {}", id);

         final WorkerDto worker = workerService.getWorkerById(id);

         log.info("Сформированные данные работника: {}", worker);

         return worker;
     }

     @PostMapping
     public Long createWorker(@RequestBody WorkerDto worker) {
         log.info("Сохранение работника: {}", worker);
         final Long id = workerService.create(worker);
         log.info("Работник сохранён с идентификатором: {}", id);
         return id;
     }

     @PostMapping("/specialization")
     public Long createSpecialization(@RequestBody SpecializationDto specialization) {
         return null;
     }

     @PostMapping("/qualification")
     public Long createQualification(@RequestBody QualificationDto qualification) {
         return null;
     }
}
