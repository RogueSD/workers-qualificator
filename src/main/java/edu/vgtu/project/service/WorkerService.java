package edu.vgtu.project.service;

import edu.vgtu.project.dto.WorkerDto;
import edu.vgtu.project.mapper.WorkerMapper;
import edu.vgtu.project.repository.WorkerRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class WorkerService {
    private final WorkerRepository repository;
    private final WorkerMapper mapper;

    public WorkerDto getWorkerById(Long workerId) {
        log.info("Поиск работника по идентификатору: {}", workerId);

        final var entity = repository.findById(workerId)
                        .orElseThrow(() -> new RuntimeException("Работник не найден!"));

        log.info("Найден работник: {}", entity);

        return mapper.toDto(entity);
    }

    public Long create(WorkerDto worker) {
        final var entity = mapper.toEntity(worker);

        log.info("Сохранение данных работника: {}", entity);

        return repository.save(entity).getId();
    }
}
