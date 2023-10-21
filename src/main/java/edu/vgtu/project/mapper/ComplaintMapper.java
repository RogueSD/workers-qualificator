package edu.vgtu.project.mapper;

import edu.vgtu.project.dto.ComplaintDto;
import edu.vgtu.project.dto.ComplaintFullDto;
import edu.vgtu.project.entity.Complaint;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper
public abstract class ComplaintMapper {
    @Mapping(target = "id", source = "complaintId")
    @Mapping(target = "content", source = "complaintContent")
    @Mapping(target = "worker", ignore = true)
    public abstract Complaint toEntity(ComplaintDto source);

    @Mapping(target = "worker.id", source = "workerId")
    public abstract Complaint toEntityFromFull(ComplaintFullDto source);

    @Mapping(target = "complaintId", source = "id")
    @Mapping(target = "complaintContent", source = "content")
    public abstract ComplaintDto toDto(Complaint source);

    @Mapping(target = "workerId", source = "worker.id")
    public abstract ComplaintFullDto toFullDto(Complaint source);

    public abstract List<ComplaintDto> toDtoList(List<Complaint> source);
}