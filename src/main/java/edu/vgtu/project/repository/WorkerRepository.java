package edu.vgtu.project.repository;

import edu.vgtu.project.entity.Worker;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public abstract class WorkerRepository implements JpaRepository<Worker, Long> {
}
