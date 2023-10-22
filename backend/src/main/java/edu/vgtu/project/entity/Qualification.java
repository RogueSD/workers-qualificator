package edu.vgtu.project.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Qualification {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;
    Long minimalManufacturedProducts;
    Double maximalDefectiveProductsPercentage;
    String name;

    @ManyToOne
    @JoinColumn(name = "specializationId", nullable = false)
    Specialization specialization;
}
