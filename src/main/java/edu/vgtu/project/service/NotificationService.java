package edu.vgtu.project.service;

import edu.vgtu.project.dto.QualificationDto;
import edu.vgtu.project.entity.Qualification;
import edu.vgtu.project.entity.Worker;
import edu.vgtu.project.repository.WorkerRepository;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Slf4j
@Service
@RequiredArgsConstructor
public class NotificationService {
    public static final double EPSILON = 0.01;
    @Value("${notification.thread-pool}")
    private int threadPoolSize;
    @Value("${notification.recipient}")
    private String recipient;
    private ExecutorService executorService;

    private final EmailService emailService;
    private final QualificationService qualificationService;
    private final WorkerRepository workerRepository;

    @PostConstruct
    protected void initialize() {
        executorService = Executors.newFixedThreadPool(threadPoolSize);
    }

    public void checkConditionsAndNotify(Worker worker) {
        executorService.submit(() -> checkWorkerQualification(worker));
    }

    private void checkWorkerQualification(Worker worker) {
        log.info("Идёт проверка соответствия сотрудника квалиификации");

        final Qualification correctQualification = qualificationService.findCorrectQualification(
                worker.getQualification().getSpecialization().getId(),
                worker.getManufacturedProductsCount(),
                worker.getDefectiveProductsCount()
        );

        if (!Objects.equals(worker.getQualification().getId(), correctQualification.getId())) {
            log.info("В соответсвии со статистикой работника, ему присвоена квалификация с идентификатором: {}", correctQualification.getId());

            worker.setQualification(correctQualification);
            final Worker savedWorker = workerRepository.save(worker);

            emailService.notifyQualificationUpdate(savedWorker, recipient);
        }
        else {
            log.info("Статистика работника соответствует его текущей квалификации, оповещение не будет отправлено.");
        }
    }
}
