package edu.vgtu.project.mapper;

import edu.vgtu.project.dto.QualificationDto;
import edu.vgtu.project.entity.Qualification;
import org.mapstruct.Builder;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(
        componentModel = "spring",
        builder = @Builder(disableBuilder = true),
        uses = {
            SpecializationMapper.class
        }
)
public abstract class QualificationMapper {
    @Mapping(target = "name", source = "qualificationName")
    @Mapping(target = "id", source = "id")
    @Mapping(target = "minimalManufacturedProducts", source = "manufacturedProductCount")
    @Mapping(target = "maximalDefectiveProductsPercentage", source = "defectiveProductsPercentage")
    @Mapping(target = "specialization", source = "specialization")
    public abstract Qualification toEntity(QualificationDto source);

    @Mapping(target = "id", source = "id")
    @Mapping(target = "qualificationName", source = "name")
    @Mapping(target = "manufacturedProductCount", source = "minimalManufacturedProducts")
    @Mapping(target = "defectiveProductsPercentage", source = "maximalDefectiveProductsPercentage")
    public abstract QualificationDto toDto(Qualification source);

    public abstract List<QualificationDto> toDtoList(List<Qualification> source);
}
