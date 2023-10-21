package edu.vgtu.project.service;

import edu.vgtu.project.dto.ComplaintDto;
import edu.vgtu.project.dto.ComplaintFullDto;
import edu.vgtu.project.entity.Complaint;
import edu.vgtu.project.exception.BusinessException;
import edu.vgtu.project.mapper.ComplaintMapper;
import edu.vgtu.project.repository.ComplaintRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class ComplaintService {
    private final ComplaintRepository repository;
    private final ComplaintMapper mapper;

    public ComplaintFullDto find(Long id) {
        log.info("Получен запрос на поиск жалобы с идентификатором: {}", id);

        return repository.findById(id)
                .map(mapper::toFullDto)
                .orElseThrow(() -> new BusinessException(404, "Жалоба не найдена!", null));
    }

    public List<ComplaintDto> findAllByWorkerId(Long workerId) {
        log.info("Поиск всех жалоб по идентификатору работника: {}", workerId);

        List<Complaint> allByWorkerId = repository.findAllByWorkerId(workerId);

        log.info("Найдено жалоб: {}", allByWorkerId.size());

        return mapper.toDtoList(allByWorkerId);
    }

    public Long create(ComplaintFullDto complaint) {
        if (complaint == null || complaint.getWorkerId() == null || complaint.getContent() == null) {
            throw new BusinessException(400, "Данные не заполнены", null);
        }

        log.info("Получен запрос на создание жалобы на работника с идентификатором: {}", complaint.getWorkerId());

        final Complaint entity = repository.save(mapper.toEntityFromFull(complaint));

        log.info("Создана жалоба с идентификатором: {}", entity.getId());
        return entity.getId();
    }

    public Long update(ComplaintFullDto complaint) {
        if (complaint.getId() == null) {
            throw new BusinessException(404, "Не указан идентификатор жалобы", null);
        }

        log.info("Получен запрос на обновление жалобы с идентификатором: {}", complaint.getId());

        final Complaint entity = repository.save(mapper.toEntityFromFull(complaint));

        log.info("Создана жалоба с идентификатором: {}", entity.getId());
        return entity.getId();
    }
}
