package edu.vgtu.project.service;

import edu.vgtu.project.dto.QualificationDto;
import edu.vgtu.project.entity.Qualification;
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
    public static final double EPSILON = 0.01;
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

    public Qualification findCorrectQualification(Long specializationId, Long manufactured, Long defected) {
        final Double defectedPercent = calculateDefectedPercent(manufactured, defected);
        final List<Qualification> allBySpecializationId = qualificationRepository.findAllBySpecializationId(specializationId);

        Qualification currentQualification = qualificationRepository.findFirstBySpecializationIdOrderByMinimalManufacturedProductsAsc(specializationId);

        for (final Qualification qualification : allBySpecializationId) {
            if (calculateQualifiedFor(qualification, manufactured, defectedPercent)
                    && isQualificationHigher(qualification, currentQualification)) {
                currentQualification = qualification;
            }
        }

        return currentQualification;
    }


    protected boolean calculateQualifiedFor(Qualification qualification, Long manufactured, Double defect) {
        final long manufacturedProducts = manufactured == null  ? 0 : manufactured;
        final double defectedPercent = defect == null ? 0.0d : defect;

        return qualification.getMaximalDefectiveProductsPercentage() - defectedPercent >= EPSILON
                && qualification.getMinimalManufacturedProducts() < manufacturedProducts;
    }

    protected boolean isQualificationHigher(Qualification left, Qualification right) {
        return left.getMinimalManufacturedProducts() > right.getMinimalManufacturedProducts() ||
                left.getMaximalDefectiveProductsPercentage() - right.getMaximalDefectiveProductsPercentage() <= EPSILON;
    }

    protected double calculateDefectedPercent(Long manufactured, Long defected) {
        return defected == null || defected == 0
                ? 0.0d
                : manufactured == null || manufactured == 0
                    ? 0.0d
                    : (double) defected / (double) manufactured;
    }
}
