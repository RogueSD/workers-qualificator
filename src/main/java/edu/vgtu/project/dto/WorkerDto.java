package edu.vgtu.project.dto;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class WorkerDto {
    Long id;
    String firstName;
    String lastName;
    SpecializationDto specialization;
    QualificationDto qualification;
    Boolean isQualified;
    String lastAuditComment;
    List<ComplaintDto> complaints;
}
