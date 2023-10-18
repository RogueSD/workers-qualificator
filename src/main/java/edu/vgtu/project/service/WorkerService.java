package edu.vgtu.project.service;

import edu.vgtu.project.dto.QualificationDto;
import edu.vgtu.project.dto.SpecializationDto;
import edu.vgtu.project.dto.WorkerDto;
import edu.vgtu.project.mapper.WorkerMapper;
import edu.vgtu.project.repository.QualificationRepository;
import edu.vgtu.project.repository.SpecializationRepository;
import edu.vgtu.project.repository.WorkerRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class WorkerService {
    private final WorkerRepository workerRepository;
    private final QualificationRepository qualificationRepository;
    private final SpecializationRepository specializationRepository;
    private final WorkerMapper mapper;

    public WorkerDto getWorkerById(Long workerId) {
        log.info("Поиск работника по идентификатору: {}", workerId);

        final var entity = workerRepository.findById(workerId)
                        .orElseThrow(() -> new RuntimeException("Работник не найден!"));

        log.info("Найден работник: {}", entity);

        return mapper.toDto(entity);
    }

    public Long createWorker(WorkerDto worker) {
        final var entity = mapper.toEntity(worker);
        log.info("Сохранение данных работника: {}", entity);
        return workerRepository.save(entity).getId();
    }

    public void updateWorker(WorkerDto worker) {
        final var entity = workerRepository.findById(worker.getId())
                .orElseThrow(() -> new RuntimeException("Работник не найден!"));

        mapper.updateEntity(entity, worker);

        workerRepository.save(entity);
    }

    public Long createQualification(QualificationDto qualification) {
        final var entity = mapper.toEntity(qualification);
        log.info("Сохранение данных квалификации: {}", entity);
        return qualificationRepository.save(entity).getId();
    }

    public Long createSpecialization(SpecializationDto specializationDto) {
        final var entity = mapper.toEntity(specializationDto);
        log.info("Сохранение данных профессии: {}", entity);
        return specializationRepository.save(entity).getId();
    }
}
