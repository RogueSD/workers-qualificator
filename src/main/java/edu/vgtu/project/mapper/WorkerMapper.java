package edu.vgtu.project.mapper;

import edu.vgtu.project.dto.ComplaintDto;
import edu.vgtu.project.dto.WorkerDto;
import edu.vgtu.project.entity.Complaint;
import edu.vgtu.project.entity.Worker;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public abstract class WorkerMapper {
    @Mapping(target = "specialization", source = "qualification.specialization.name")
    @Mapping(target = "lastName", source = "surname")
    @Mapping(target = "lastAuditComment", source = "auditResults")
    @Mapping(target = "firstName", source = "name")
    @Mapping(target = "id", source = "id")
    @Mapping(target = "qualification", source = "qualification.name")
    @Mapping(target = "complaints", source = "complaints")
    public abstract WorkerDto toDto(Worker source);

    @Mapping(target = "complaintId", source = "id")
    @Mapping(target = "complaintContent", source = "content")
    protected abstract ComplaintDto toDto(Complaint source);
}
