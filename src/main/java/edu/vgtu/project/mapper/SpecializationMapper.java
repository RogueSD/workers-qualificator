package edu.vgtu.project.mapper;

import edu.vgtu.project.dto.SpecializationDto;
import edu.vgtu.project.dto.utils.PageDto;
import edu.vgtu.project.entity.Specialization;
import org.mapstruct.Builder;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.data.domain.Page;

@Mapper(
        componentModel = "spring",
        builder = @Builder(disableBuilder = true)
)
public abstract class SpecializationMapper {
    @Mapping(target = "id", source = "id")
    @Mapping(target = "name", source = "specializationName")
    public abstract Specialization toEntity(SpecializationDto source);

    @Mapping(target = "id", source = "id")
    @Mapping(target = "specializationName", source = "name")
    public abstract SpecializationDto toDto(Specialization source);

    @Mapping(target = "currentPage", source = "number")
    @Mapping(target = "totalCount", source = "totalElements")
    @Mapping(target = "content", source = "content")
    public abstract PageDto<SpecializationDto> toPage(Page<Specialization> source);
}
