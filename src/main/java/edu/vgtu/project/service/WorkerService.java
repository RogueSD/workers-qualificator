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
}
