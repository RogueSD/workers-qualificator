package edu.vgtu.project.service;

import edu.vgtu.project.dto.WorkerEditDto;
import edu.vgtu.project.dto.WorkerViewDto;
import edu.vgtu.project.dto.WorkerShortDto;
import edu.vgtu.project.dto.utils.PageDto;
import edu.vgtu.project.entity.Worker;
import edu.vgtu.project.exception.BusinessException;
import edu.vgtu.project.mapper.WorkerMapper;
import edu.vgtu.project.repository.WorkerRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class WorkerService {
    private final WorkerRepository workerRepository;
    private final WorkerMapper workerMapper;
    private final NotificationService notificationService;
    private final QualificationService qualificationService;

    public WorkerViewDto getWorkerById(Long workerId) {
        return workerRepository.findById(workerId)
                .map(workerMapper::toViewDto)
                .orElseThrow(() -> new BusinessException(404, "Работник не найден!", null));
    }

    public Long create(WorkerEditDto worker) {
        if (worker == null || worker.getSpecialization() == null || worker.getSpecialization().getId() == null) {
            throw new BusinessException(400, "Некорректные данные работника", null);
        }


        Worker entity = workerMapper.toEntity(worker);
        entity.setQualification(
                qualificationService.findCorrectQualification(
                        worker.getSpecialization().getId(),
                        worker.getManufacturedProducts(),
                        worker.getDefectedProducts()
                )
        );

        return workerRepository.save(entity).getId();
    }

    public void update(WorkerEditDto worker) {
        if (worker == null || worker.getId() == null) {
            throw new BusinessException(400, "Некорректные данные работника", null);
        }

        final Worker entity = workerRepository.findById(worker.getId())
                .orElseThrow(() -> new BusinessException(404, "Работник не найден!", null));

        final Long previousSpecializationId = entity.getQualification().getSpecialization().getId();
        final Long currentSpecializationId = worker.getSpecialization() == null
                ? null
                : worker.getSpecialization().getId();

        if (currentSpecializationId != null && !Objects.equals(previousSpecializationId, currentSpecializationId)) {
            worker.setDefectedProducts(0L);
            worker.setManufacturedProducts(0L);

            entity.setQualification(
                    qualificationService.findCorrectQualification(
                        currentSpecializationId,
                        worker.getManufacturedProducts(),
                        worker.getDefectedProducts()
                    )
            );

        }

        workerMapper.updateEntity(entity, worker);
        workerRepository.save(entity);

        // Только если не было смены профессии
        if (currentSpecializationId == null || Objects.equals(previousSpecializationId, currentSpecializationId)) {
            notificationService.checkConditionsAndNotify(entity);
        }
    }

    public PageDto<WorkerShortDto> getPage(Long page, Long size, Sort.Direction direction) {
        return workerMapper.toPage(
                workerRepository.findAll(
                        PageRequest.of(page.intValue(), size.intValue(), Sort.by(direction, "id"))
                )
        );
    }

    public List<WorkerViewDto> getAllWorkers() {
        return workerRepository.findAll().stream()
                .map(workerMapper::toViewDto)
                .collect(Collectors.toList());
    }
}
