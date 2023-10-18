package edu.vgtu.project.mapper;

import edu.vgtu.project.dto.ComplaintDto;
import edu.vgtu.project.dto.QualificationDto;
import edu.vgtu.project.dto.SpecializationDto;
import edu.vgtu.project.dto.WorkerDto;
import edu.vgtu.project.entity.Complaint;
import edu.vgtu.project.entity.Qualification;
import edu.vgtu.project.entity.Specialization;
import edu.vgtu.project.entity.Worker;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

@Mapper(componentModel = "spring")
public abstract class WorkerMapper {
    public static final double EPSILON = 0.01;

    @Mapping(target = "defectiveProductsCount", constant = "0L")
    @Mapping(target = "manufacturedProductsCount", constant = "0L")
    @Mapping(target = "name", source = "firstName")
    @Mapping(target = "surname", source = "lastName")
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "auditResults", source = "lastAuditComment")
    @Mapping(target = "qualification", source = "qualification")
    @Mapping(target = "complaints", source = "complaints")
    public abstract Worker toEntity(WorkerDto source);

    @Mapping(target = "name", source = "qualificationName")
    @Mapping(target = "id", source = "id")
    @Mapping(target = "minimalManufacturedProducts", source = "manufacturedProductCount")
    @Mapping(target = "maximalDefectiveProductsPercentage", source = "defectiveProductsPercentage")
    @Mapping(target = "specialization", source = "specialization")
    public abstract Qualification toEntity(QualificationDto source);

    @Mapping(target = "id", source = "id")
    @Mapping(target = "name", source = "specializationName")
    public abstract Specialization toEntity(SpecializationDto source);

    @Mapping(target = "id", source = "complaintId")
    @Mapping(target = "content", source = "complaintContent")
    @Mapping(target = "worker", ignore = true)
    public abstract Complaint toEntity(ComplaintDto source);

    @Mapping(target = "lastName", source = "surname")
    @Mapping(target = "lastAuditComment", source = "auditResults")
    @Mapping(target = "firstName", source = "name")
    @Mapping(target = "id", source = "id")
    @Mapping(target = "qualification", source = "qualification")
    @Mapping(target = "complaints", source = "complaints")
    @Mapping(target = "isQualified", source = ".", qualifiedByName = "calculateQualification")
    public abstract WorkerDto toDto(Worker source);

    @Mapping(target = "complaintId", source = "id")
    @Mapping(target = "complaintContent", source = "content")
    protected abstract ComplaintDto toDto(Complaint source);

    @Mapping(target = "id", source = "id")
    @Mapping(target = "qualificationName", source = "name")
    @Mapping(target = "manufacturedProductCount", source = "minimalManufacturedProducts")
    @Mapping(target = "defectiveProductsPercentage", source = "maximalDefectiveProductsPercentage")
    protected abstract QualificationDto toDto(Qualification source);

    @Mapping(target = "id", source = "id")
    @Mapping(target = "specializationName", source = "name")
    protected abstract SpecializationDto toDto(Specialization source);

    @Named("calculateQualification")
    protected boolean calculateQualified(Worker worker) {
        if (worker == null || worker.getQualification() == null) {
            return true;
        }

        Long made = worker.getManufacturedProductsCount();

        if (made == null || made == 0L) {
            return false;
        }

        long defective = worker.getDefectiveProductsCount() == null ? 0L : worker.getDefectiveProductsCount();

        double percentage = (double) defective / (double) made;

        final var qualification = worker.getQualification();

        final long minimal = qualification.getMinimalManufacturedProducts() == null ? 0L : qualification.getMinimalManufacturedProducts();

        return (+(qualification.getMaximalDefectiveProductsPercentage() - percentage)) <= EPSILON
                && ( minimal == 0L || made >= minimal);
    }

}
