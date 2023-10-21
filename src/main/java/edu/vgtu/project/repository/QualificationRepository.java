package edu.vgtu.project.repository;

import edu.vgtu.project.entity.Qualification;
import edu.vgtu.project.entity.Specialization;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface QualificationRepository extends JpaRepository<Qualification, Long> {
    List<Qualification> findAllBySpecialization(Specialization specialization);
    List<Qualification> findAllBySpecializationId(Long specializationId);
}
