package edu.vgtu.project.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.Set;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Worker {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;
    String name;
    String surname;
    Long manufacturedProductsCount;
    Long defectiveProductsCount;
    String auditResults;

    @ManyToOne
    @JoinColumn(name = "qualificationId", nullable = false)
    Qualification qualification;

    @OneToMany(mappedBy = "worker", cascade = CascadeType.ALL)
    Set<Complaint> complaints;

    public double getDefectedProducts() {
        return 0;
    }
}
