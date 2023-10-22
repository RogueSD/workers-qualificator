package edu.vgtu.project.repository;

import edu.vgtu.project.entity.Specialization;

import org.springframework.data.jpa.repository.JpaRepository;

public interface SpecializationRepository extends JpaRepository<Specialization, Long> {
    
}
