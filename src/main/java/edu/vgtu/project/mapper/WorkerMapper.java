package edu.vgtu.project.mapper;

import edu.vgtu.project.dto.WorkerEditDto;
import edu.vgtu.project.dto.WorkerViewDto;
import edu.vgtu.project.dto.WorkerShortDto;
import edu.vgtu.project.dto.utils.PageDto;
import edu.vgtu.project.entity.Worker;
import jdk.jfr.Name;
import org.apache.commons.lang3.StringUtils;
import org.mapstruct.*;
import org.springframework.data.domain.Page;

@Mapper(
        componentModel = "spring",
        builder = @Builder(disableBuilder = true),
        uses = {
                ComplaintMapper.class,
                QualificationMapper.class
        },
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE
)
public abstract class WorkerMapper {
    public static final double EPSILON = 0.01;

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "complaints", ignore = true)
    @Mapping(target = "qualification", ignore = true)
    @Mapping(target = "defectiveProductsCount", source = "defectedProducts")
    @Mapping(target = "manufacturedProductsCount", source = "manufacturedProducts")
    @Mapping(target = "name", source = "firstName")
    @Mapping(target = "surname", source = "lastName")
    @Mapping(target = "auditResults", source = "auditComment")
    public abstract Worker toEntity(WorkerEditDto source);

    @Mapping(target = "id", source = "id")
    @Mapping(target = "qualification", source = "qualification")
    @Mapping(target = "isQualified", source = ".", qualifiedByName = "calculateQualification")
    @Mapping(target = "fullName", source = ".", qualifiedByName = "toFullName")
    public abstract WorkerShortDto toShortDto(Worker source);

    @Mapping(target = "currentPage", source = "number")
    @Mapping(target = "totalCount", source = "totalElements")
    @Mapping(target = "content", source = "content")
    public abstract PageDto<WorkerShortDto> toPage(Page<Worker> source);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "complaints", ignore = true)
    @Mapping(target = "qualification", ignore = true)
    @Mapping(target = "defectiveProductsCount", source = "defectedProducts")
    @Mapping(target = "manufacturedProductsCount", source = "manufacturedProducts")
    @Mapping(target = "name", source = "firstName")
    @Mapping(target = "surname", source = "lastName")
    @Mapping(target = "auditResults", source = "auditComment")
    public abstract void updateEntity(@MappingTarget Worker target, WorkerEditDto source);


    @Mapping(target = "lastName", source = "surname")
    @Mapping(target = "auditComment", source = "auditResults")
    @Mapping(target = "firstName", source = "name")
    @Mapping(target = "id", source = "id")
    @Mapping(target = "qualification", source = "qualification")
    @Mapping(target = "complaints", source = "complaints")
    @Mapping(target = "isQualified", source = ".", qualifiedByName = "calculateQualification")
    @Mapping(target = "manufacturedProducts", source = "manufacturedProductsCount")
    @Mapping(target = "defectedProductsPercent", source = ".", qualifiedByName = "calculateDefectedPercent")
    public abstract WorkerViewDto toViewDto(Worker source);

    @Named("calculateQualification")
    protected boolean calculateQualified(Worker source) {
        if (source == null || source.getQualification() == null) {
            return true;
        }

        Long made = source.getManufacturedProductsCount();

        if (made == null || made == 0L) {
            return false;
        }

        long defective = source.getDefectiveProductsCount() == null ? 0L : source.getDefectiveProductsCount();

        double percentage = (double) defective / (double) made;

        final var qualification = source.getQualification();

        final long minimal = qualification.getMinimalManufacturedProducts() == null ? 0L : qualification.getMinimalManufacturedProducts();

        return (qualification.getMaximalDefectiveProductsPercentage() - percentage) >= EPSILON
                && ( minimal == 0L || made >= minimal);
    }

    @Named("calculateDefectedPercent")
    protected Double calculateDefectedPercent(Worker source) {
        if (source == null
                || source.getDefectiveProductsCount() == null
                || source.getManufacturedProductsCount() == null
                || source.getDefectiveProductsCount() == 0
                || source.getManufacturedProductsCount() == 0) {
            return 0.0d;
        }

        return (double) source.getDefectiveProductsCount()
                / (double) source.getManufacturedProductsCount();
    }

    @Named("toFullName")
    protected String getFullName(Worker dto) {
        if (dto == null) {
            return null;
        }

        return StringUtils.joinWith(" ", dto.getSurname(), dto.getName());
    }
}
