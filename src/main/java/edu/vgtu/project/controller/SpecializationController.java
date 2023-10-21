package edu.vgtu.project.controller;

import edu.vgtu.project.dto.SpecializationDto;
import edu.vgtu.project.dto.utils.PageDto;
import edu.vgtu.project.service.SpecializationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/specialization")
@RequiredArgsConstructor
public class SpecializationController {
    private final SpecializationService specializationService;

    @GetMapping("/{id}")
    public SpecializationDto getSpecializationById(@PathVariable Long id) {
        log.info("Поиск профессии по идентификатору: {}", id);
        final SpecializationDto specializationDto = specializationService.find(id);
        log.info("Найдены данные професии: {}", specializationDto);
        return specializationDto;
    }

    @GetMapping
    public PageDto<SpecializationDto> getSpecializationPage(@RequestParam(defaultValue = "0") Long page,
                                                            @RequestParam(defaultValue = "10") Long size,
                                                            @RequestParam(defaultValue = "ASC") Sort.Direction direction) {

        log.info("Получение страницы профессий: страница - {}, размер - {}, сортировка - {}", page, size, direction);
        PageDto<SpecializationDto> data = specializationService.getPage(page, size, direction);
        log.info("Полученная страница: {}", data);
        return data;
    }

    @PostMapping
    public Long createSpecialization(@RequestBody SpecializationDto specialization) {
        log.info("Сохранение профессии: {}", specialization);
        final Long id = specializationService.create(specialization);
        log.info("Профессия сохранёна с идентификатором: {}", id);
        return id;
    }

    @PutMapping
    public void updateSpecialization(@RequestBody SpecializationDto specialization) {
        log.info("Обновление профессии: {}", specialization);
        specializationService.update(specialization);
        log.info("Профессия обновлена");
    }
}
