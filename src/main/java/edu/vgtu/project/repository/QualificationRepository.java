package edu.vgtu.project.repository;

import edu.vgtu.project.entity.Qualification;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface QualificationRepository extends JpaRepository<Qualification, Long> {
    List<Qualification> findAllBySpecializationId(Long specializationId);
}
