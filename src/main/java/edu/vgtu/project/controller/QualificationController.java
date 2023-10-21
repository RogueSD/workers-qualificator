package edu.vgtu.project.controller;

import edu.vgtu.project.dto.QualificationDto;
import edu.vgtu.project.service.QualificationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/specialization")
@RequiredArgsConstructor
public class QualificationController {
    private final QualificationService qualificationService;

    @GetMapping("/{id}")
    public QualificationDto getQualificationById(@PathVariable Long id) {
        log.info("Поиск квалификации по идентификатору: {}", id);
        QualificationDto qualificationDto = qualificationService.find(id);
        log.info("Найдены данные квалификации: {}", qualificationDto);
        return qualificationDto;
    }

    @GetMapping("/specialization/{specializationId}")
    public List<QualificationDto> findAllQualificationsBySpecializationId(Long specializationId) {
        log.info("Поиск всех квалификаций по идетификатору профессии: {}", specializationId);
        List<QualificationDto> allBySpecializationId = qualificationService.findAllBySpecializationId(specializationId);
        log.info("Найдено квалификаций: {}", allBySpecializationId.size());
        return allBySpecializationId;
    }

    @PostMapping
    public Long createQualification(@RequestBody QualificationDto qualification) {
        log.info("Сохранение квалификации: {}", qualification);
        final Long id = qualificationService.create(qualification);
        log.info("Квалификация сохранёна с идентификатором: {}", id);
        return id;
    }

    @PutMapping
    public Long updateQualification(@RequestBody QualificationDto qualification) {
        log.info("Сохранение квалификации: {}", qualification);
        final Long id = qualificationService.create(qualification);
        log.info("Квалификация сохранёна с идентификатором: {}", id);
        return id;
    }
}
