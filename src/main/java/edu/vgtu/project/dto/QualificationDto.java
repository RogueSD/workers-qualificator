package edu.vgtu.project.dto;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class QualificationDto {
    Long id;
    String qualificationName;
    Long manufacturedProductCount;
    Double defectiveProductsPercentage;
    SpecializationDto specialization;
}
