package edu.vgtu.project.dto;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class WorkerEditDto {
    Long id;
    String lastName;
    String firstName;
    String auditComment;
    SpecializationDto specialization;
    Long manufacturedProducts;
    Long defectedProducts;
}
