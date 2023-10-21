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
        return repository.findById(id)
                .map(mapper::toFullDto)
                .orElseThrow(() -> new BusinessException(404, "Жалоба не найдена!", null));
    }

    public List<ComplaintDto> findAllByWorkerId(Long workerId) {
        return mapper.toDtoList(
                repository.findAllByWorkerId(workerId)
        );
    }

    public Long create(ComplaintFullDto complaint) {
        if (complaint == null || complaint.getWorkerId() == null || complaint.getContent() == null) {
            throw new BusinessException(400, "Данные жалобы не заполнены", null);
        }

        return repository.save(
                mapper.toEntityFromFull(complaint)
        ).getId();
    }

    public void update(ComplaintFullDto complaint) {
        if (complaint == null || complaint.getId() == null) {
            throw new BusinessException(404, "Не указан идентификатор жалобы", null);
        }

        final Complaint entity = repository.findById(complaint.getId())
                .orElseThrow(() -> new BusinessException(404, "Не найдены данные жалобы", null));

        mapper.updateEntity(entity, complaint);

        repository.save(entity);
    }
}
