package edu.vgtu.project.service;

import edu.vgtu.project.dto.QualificationDto;
import edu.vgtu.project.dto.SpecializationDto;
import edu.vgtu.project.dto.WorkerDto;
import edu.vgtu.project.dto.WorkerShortDto;
import edu.vgtu.project.dto.utils.PageDto;
import edu.vgtu.project.entity.Qualification;
import edu.vgtu.project.entity.Specialization;
import edu.vgtu.project.entity.Worker;
import edu.vgtu.project.mapper.WorkerMapper;
import edu.vgtu.project.repository.QualificationRepository;
import edu.vgtu.project.repository.SpecializationRepository;
import edu.vgtu.project.repository.WorkerRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

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

    public PageDto<WorkerShortDto> getWorkerList(Long page, Long size, Sort.Direction direction) {
        log.info("Получен запрос на получение страницы рабоников");
        Page<Worker> workers = workerRepository.getWorkers(PageRequest.of(page.intValue(), size.intValue(), Sort.by(direction, "id")));
        return mapper.toPage(workers);
    }

    public List<WorkerDto> getAllWorkers() {
        return workerRepository.findAll().stream()
                .map(mapper::toDto)
                .collect(Collectors.toList());
    }

    public void checkWorkerQualification(Long workerId) {
        Worker worker = workerRepository.findById(workerId).orElseThrow(
            () -> new RuntimeException("Работник не найден!")
        );
        Specialization specialization = worker.getQualification().getSpecialization();
        List<Qualification> qualifications = qualificationRepository.findAllBySpecialization(specialization);
        Qualification currentQualification = worker.getQualification();
        for (Qualification qualification : qualifications)
            // check if the qualification is relevant for worker
            if ((qualification.getMinimalManufacturedProducts() < worker.getManufacturedProductsCount() && 
                qualification.getMaximalDefectiveProductsPercentage() > (double)worker.getDefectedProducts()/(double)worker.getManufacturedProductsCount())
                && // check if the qualification is higher than current
                (currentQualification.getMinimalManufacturedProducts() < qualification.getMinimalManufacturedProducts() || 
                currentQualification.getMaximalDefectiveProductsPercentage() > qualification.getMaximalDefectiveProductsPercentage()))
                    currentQualification = qualification;
        
    }
}
