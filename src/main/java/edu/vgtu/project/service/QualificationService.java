package edu.vgtu.project.service;

import edu.vgtu.project.dto.QualificationDto;
import edu.vgtu.project.exception.BusinessException;
import edu.vgtu.project.mapper.QualificationMapper;
import edu.vgtu.project.repository.QualificationRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class QualificationService {
    private final QualificationRepository qualificationRepository;
    private final QualificationMapper qualificationMapper;

    public List<QualificationDto> findAllBySpecializationId(Long specializationId) {
        if (specializationId == null) {
            throw new BusinessException(400, "Не указан идентификатор специализации", null);
        }

        return qualificationMapper.toDtoList(
                qualificationRepository.findAllBySpecializationId(specializationId)
        );
    }

    public QualificationDto find(Long id) {
        return qualificationRepository.findById(id)
                .map(qualificationMapper::toDto)
                .orElseThrow(() -> new BusinessException(404, "Квалификация не найдена!", null));
    }

    public Long create(QualificationDto qualification) {
        if(qualification == null || qualification.getSpecialization() == null || qualification.getSpecialization().getId() == null) {
            throw new BusinessException(400, "Данные квалификации некорректны", null);
        }

        return qualificationRepository.save(
                qualificationMapper.toEntity(qualification)
        ).getId();
    }

    public void update(QualificationDto qualification) {
        if(qualification == null || qualification.getId() == null || qualification.getSpecialization() == null || qualification.getSpecialization().getId() == null) {
            throw new BusinessException(400, "Данные квалификации некорректны", null);
        }

        qualificationRepository.save(
                qualificationMapper.toEntity(qualification)
        );
    }
}
