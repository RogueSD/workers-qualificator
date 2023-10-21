package edu.vgtu.project.service;

import edu.vgtu.project.dto.WorkerDto;
import edu.vgtu.project.dto.WorkerShortDto;
import edu.vgtu.project.dto.utils.PageDto;
import edu.vgtu.project.entity.Worker;
import edu.vgtu.project.exception.BusinessException;
import edu.vgtu.project.mapper.WorkerMapper;
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
    private final WorkerMapper workerMapper;

    public WorkerDto getWorkerById(Long workerId) {
        return workerRepository.findById(workerId)
                .map(workerMapper::toDto)
                .orElseThrow(() -> new BusinessException(404, "Работник не найден!", null));
    }

    public Long create(WorkerDto worker) {
        if (worker == null || worker.getQualification() == null) {
            throw new BusinessException(400, "Некорректные данные работника", null);
        }

        return workerRepository.save(
                workerMapper.toEntity(worker)
        ).getId();
    }

    public void update(WorkerDto worker) {
        if (worker == null || worker.getId() == null || worker.getQualification() == null) {
            throw new BusinessException(400, "Некорректные данные работника", null);
        }

        final var entity = workerRepository.findById(worker.getId())
                .orElseThrow(() -> new BusinessException(404, "Работник не найден!", null));

        workerMapper.updateEntity(entity, worker);

        workerRepository.save(entity);

        checkWorkerQualification(entity.getId());
    }

    public PageDto<WorkerShortDto> getPage(Long page, Long size, Sort.Direction direction) {
        return workerMapper.toPage(
                workerRepository.findAll(
                        PageRequest.of(page.intValue(), size.intValue(), Sort.by(direction, "id"))
                )
        );
    }

    public List<WorkerDto> getAllWorkers() {
        return workerRepository.findAll().stream()
                .map(workerMapper::toDto)
                .collect(Collectors.toList());
    }

    public void checkWorkerQualification(Long workerId) {
        Worker worker = workerRepository.findById(workerId)
                        .orElseThrow(() -> new RuntimeException("Работник не найден!"));

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
        if (worker.getQualification() != currentQualification)
        {
            worker.setQualification(currentQualification);
            workerQualificationUpdateEmailService.notifyQualificationUpdate(worker, "nikita-saprygin@mail.ru", "nikita-saprygin@mail.ru");
            workerRepository.save(worker);
        }
    }
}
