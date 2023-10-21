package edu.vgtu.project.controller;

import edu.vgtu.project.dto.QualificationDto;
import edu.vgtu.project.dto.SpecializationDto;
import edu.vgtu.project.dto.WorkerDto;
import edu.vgtu.project.dto.WorkerShortDto;
import edu.vgtu.project.dto.utils.PageDto;
import edu.vgtu.project.service.EmailService;
import edu.vgtu.project.service.WorkerService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/worker")
@RequiredArgsConstructor
public class WorkerController {
     private final WorkerService workerService;
     private final EmailService workerQualificationUpdateEmailService;

     @GetMapping(value = "/{id}")
     public WorkerDto getWorkerById(@PathVariable Long id) {
         log.info("Получен запрос на получение данных работника с идентификатором: {}", id);

         final WorkerDto worker = workerService.getWorkerById(id);

         log.info("Сформированные данные работника: {}", worker);

         return worker;
     }

     @PutMapping
     public Long updateWorker(@RequestBody WorkerDto worker) {
         log.info("Получен запрос на обновление данных работника");
         workerService.updateWorker(worker);
         return worker.getId();
     }

     @PostMapping
     public Long createWorker(@RequestBody WorkerDto worker) {
         log.info("Сохранение работника: {}", worker);
         final Long id = workerService.createWorker(worker);
         log.info("Работник сохранён с идентификатором: {}", id);
         return id;
     }

     @PostMapping("/specialization")
     public Long createSpecialization(@RequestBody SpecializationDto specialization) {
         log.info("Сохранение профессии: {}", specialization);
         final Long id = workerService.createSpecialization(specialization);
         log.info("Профессия сохранёна с идентификатором: {}", id);
         return id;
     }

     @PostMapping("/qualification")
     public Long createQualification(@RequestBody QualificationDto qualification) {
         log.info("Сохранение квалификации: {}", qualification);
         final Long id = workerService.createQualification(qualification);
         log.info("Квалификация сохранёна с идентификатором: {}", id);
         return id;
     }

     @GetMapping
     public PageDto<WorkerShortDto> getWorkersPage(@RequestParam(defaultValue = "0") Long page, @RequestParam(defaultValue = "10") Long size, @RequestParam(defaultValue = "ASC") Sort.Direction direction) {
         log.info("Запрос страницы");
         return workerService.getWorkerList(page, size, direction);
     }
}
