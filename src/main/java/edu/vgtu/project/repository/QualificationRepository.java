package edu.vgtu.project.repository;

import edu.vgtu.project.entity.Qualification;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface QualificationRepository extends JpaRepository<Qualification, Long> {
    @Query(
        value = "SELECT * FROM qualification WHERE qualification.specializationId = (SELECT )",
        nativeQuery = true)
        List<Qualification> getAllPossibleQualificationsOfWorker(@Param("workerId") Long workerId);
}
