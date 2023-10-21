package edu.vgtu.project.repository;

import edu.vgtu.project.entity.Worker;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WorkerRepository extends JpaRepository<Worker, Long> {
    Page<Worker> getWorkers(PageRequest pageable);
}
