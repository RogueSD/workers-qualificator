package edu.vgtu.project.dto.rows;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class WorkerRowDto {
    Long id;
    String firstName;
    String lastName;
    String qualificationName;
    Long manufacturedProductCount;
    Double defectiveProductsPercentage;
    Boolean isQualified;
    String auditComment;
    String complaints;
    Long manufacturedProducts;
    Double defectedProductsPercent;
}