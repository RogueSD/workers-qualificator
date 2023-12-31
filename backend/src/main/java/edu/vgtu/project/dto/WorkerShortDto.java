package edu.vgtu.project.dto;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class WorkerShortDto {
    Long id;
    String fullName;
    QualificationDto qualification;
    Boolean isQualified;
}
