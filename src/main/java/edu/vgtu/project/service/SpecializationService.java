package edu.vgtu.project.service;

import edu.vgtu.project.dto.SpecializationDto;
import edu.vgtu.project.dto.utils.PageDto;
import edu.vgtu.project.entity.Specialization;
import edu.vgtu.project.exception.BusinessException;
import edu.vgtu.project.mapper.SpecializationMapper;
import edu.vgtu.project.repository.SpecializationRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class SpecializationService {
    private final SpecializationRepository specializationRepository;
    private final SpecializationMapper specializationMapper;

    public SpecializationDto find(Long id) {
        return specializationRepository.findById(id)
                .map(specializationMapper::toDto)
                .orElseThrow(() -> new BusinessException(404, "Специализация не найдена", null));
    }

    public PageDto<SpecializationDto> getPage(Long page, Long size, Sort.Direction direction) {
        return specializationMapper.toPage(
                specializationRepository.findAll(
                        PageRequest.of(page.intValue(), size.intValue(), Sort.by(direction, "id"))
                )
        );
    }

    public Long create(SpecializationDto specialization) {
        if (specialization == null || specialization.getSpecializationName() == null) {
            throw new BusinessException(400, "Данные профессии некорректны", null);
        }

        return specializationRepository.save(
                specializationMapper.toEntity(specialization)
        ).getId();
    }

    public void update(SpecializationDto specialization) {
        if (specialization == null || specialization.getId() == null) {
            throw new BusinessException(400, "Данные профессии некорректны, не указан идентификатор!", null);
        }

        final Specialization entity = specializationRepository.findById(specialization.getId())
                .orElseThrow(() -> new BusinessException(404, "Данные профессии не найдены!", null));

        specializationMapper.updateEntity(entity, specialization);

        specializationRepository.save(entity);
    }
}
