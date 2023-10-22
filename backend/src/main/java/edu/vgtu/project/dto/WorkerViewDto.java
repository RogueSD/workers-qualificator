package edu.vgtu.project.dto;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class WorkerViewDto {
    Long id;
    String firstName;
    String lastName;
    QualificationDto qualification;
    Boolean isQualified;
    String auditComment;
    List<ComplaintDto> complaints;
    Long manufacturedProducts;
    Double defectedProductsPercent;
}
