package edu.vgtu.project.controller;

import edu.vgtu.project.dto.WorkerDto;
import edu.vgtu.project.dto.WorkerShortDto;
import edu.vgtu.project.dto.utils.PageDto;
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

     @GetMapping(value = "/{id}")
     public WorkerDto getWorkerById(@PathVariable Long id) {
         log.info("Получен запрос на получение данных работника с идентификатором: {}", id);
         final WorkerDto worker = workerService.getWorkerById(id);
         log.info("Сформированные данные работника: {}", worker);
         return worker;
     }

    @GetMapping
    public PageDto<WorkerShortDto> getWorkersPage(@RequestParam(defaultValue = "0") Long page,
                                                  @RequestParam(defaultValue = "10") Long size,
                                                  @RequestParam(defaultValue = "ASC") Sort.Direction direction) {

        log.info("Получение страницы работников: страница - {}, размер - {}, сортировка - {}", page, size, direction);
        PageDto<WorkerShortDto> data = workerService.getPage(page, size, direction);
        log.info("Полученная страница: {}", data);
        return data;
    }

    @PostMapping
    public Long createWorker(@RequestBody WorkerDto worker) {
        log.info("Сохранение работника: {}", worker);
        final Long id = workerService.create(worker);
        log.info("Работник сохранён с идентификатором: {}", id);
        return id;
    }

     @PutMapping
     public void updateWorker(@RequestBody WorkerDto worker) {
         log.info("Получен запрос на обновление данных работника: {}", worker);
         workerService.update(worker);
         log.info("Данные работника обновлены");
     }

}
