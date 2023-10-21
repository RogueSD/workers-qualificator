package edu.vgtu.project.service;

import edu.vgtu.project.dto.QualificationDto;
import edu.vgtu.project.dto.WorkerDto;
import edu.vgtu.project.entity.Qualification;
import edu.vgtu.project.entity.Specialization;
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
        QualificationDto correctQualification = findCorrectQualification(worker);

        if (!Objects.equals(worker.getQualification().getId(), correctQualification.getId()))
        {
            worker.setQualification(
                    Qualification.builder()
                            .id(correctQualification.getId())
                            .build()
            );
            emailService.notifyQualificationUpdate(worker, "nikita-saprygin@mail.ru", "nikita-saprygin@mail.ru");
            workerRepository.save(worker);
        }
    }

    private QualificationDto findCorrectQualification(Worker worker) {
        QualificationDto currentQualification = qualificationService.find(worker.getQualification().getId());
        List<QualificationDto> qualifications = qualificationService.findAllBySpecializationId(currentQualification.getSpecialization().getId());

        for (QualificationDto qualification : qualifications) {
            if (calculateQualified(worker) && isQualificationHigher(currentQualification, qualification)) {
                currentQualification = qualification;
            }
        }

        return currentQualification;
    }

    protected boolean calculateQualified(Worker worker) {
        if (worker == null || worker.getQualification() == null) {
            return true;
        }

        Long made = worker.getManufacturedProductsCount();

        if (made == null || made == 0L) {
            return false;
        }

        long defective = worker.getDefectiveProductsCount() == null ? 0L : worker.getDefectiveProductsCount();

        double percentage = (double) defective / (double) made;

        final var qualification = worker.getQualification();

        final long minimal = qualification.getMinimalManufacturedProducts() == null ? 0L : qualification.getMinimalManufacturedProducts();

        return (+(qualification.getMaximalDefectiveProductsPercentage() - percentage)) <= EPSILON
                && ( minimal == 0L || made >= minimal);
    }

    protected boolean isQualificationHigher(QualificationDto left, QualificationDto right) {
        return left.getManufacturedProductCount() < right.getManufacturedProductCount() ||
                (+(left.getDefectiveProductsPercentage() - right.getDefectiveProductsPercentage())) <= EPSILON;
    }
}
