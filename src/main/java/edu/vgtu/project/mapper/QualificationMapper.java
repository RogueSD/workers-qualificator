package edu.vgtu.project.mapper;

import edu.vgtu.project.dto.QualificationDto;
import edu.vgtu.project.entity.Qualification;
import org.apache.commons.lang3.StringUtils;
import org.mapstruct.*;

import java.util.List;
import java.util.Objects;

@Mapper(
        componentModel = "spring",
        builder = @Builder(disableBuilder = true),
        uses = {
            SpecializationMapper.class
        },
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE
)
public abstract class QualificationMapper {
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "name", source = "qualificationName")
    @Mapping(target = "minimalManufacturedProducts", source = "manufacturedProductCount")
    @Mapping(target = "maximalDefectiveProductsPercentage", source = "defectiveProductsPercentage")
    @Mapping(target = "specialization", source = "specialization")
    public abstract Qualification toEntity(QualificationDto source);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "name", source = "qualificationName")
    @Mapping(target = "minimalManufacturedProducts", source = "manufacturedProductCount")
    @Mapping(target = "maximalDefectiveProductsPercentage", source = "defectiveProductsPercentage")
    public abstract void updateEntity(@MappingTarget Qualification target, QualificationDto source);

    @Mapping(target = "id", source = "id")
    @Mapping(target = "qualificationName", source = "name")
    @Mapping(target = "manufacturedProductCount", source = "minimalManufacturedProducts")
    @Mapping(target = "defectiveProductsPercentage", source = "maximalDefectiveProductsPercentage")
    public abstract QualificationDto toDto(Qualification source);

    public abstract List<QualificationDto> toDtoList(List<Qualification> source);
}
