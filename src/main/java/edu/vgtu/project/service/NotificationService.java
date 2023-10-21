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
            Worker savedWorker = workerRepository.save(worker);
            emailService.notifyQualificationUpdate(savedWorker, "nikita-saprygin@mail.ru");
        }
    }

    private QualificationDto findCorrectQualification(Worker worker) {
        QualificationDto currentQualification = qualificationService.find(worker.getQualification().getId());
        List<QualificationDto> qualifications = qualificationService.findAllBySpecializationId(currentQualification.getSpecialization().getId());

        for (QualificationDto qualification : qualifications) {
            if (calculateWorkerQualifiedFor(worker, qualification) && isQualificationHigher(qualification, currentQualification)) {
                currentQualification = qualification;
            }
        }

        return currentQualification;
    }

    protected boolean calculateWorkerQualifiedFor(Worker worker, QualificationDto qualification) {
        if (worker == null || worker.getQualification() == null) {
            return true;
        }

        Long made = worker.getManufacturedProductsCount();

        if (made == null || made == 0L) {
            return false;
        }

        long defective = worker.getDefectiveProductsCount() == null ? 0L : worker.getDefectiveProductsCount();

        double percentage = (double) defective / (double) made;

        final long minimal = qualification.getManufacturedProductCount() == null ? 0L : qualification.getManufacturedProductCount();

        return qualification.getDefectiveProductsPercentage() - percentage >= EPSILON
                && ( minimal == 0L || made >= minimal);
    }

    protected boolean isQualificationHigher(QualificationDto left, QualificationDto right) {
        return left.getManufacturedProductCount() > right.getManufacturedProductCount() ||
                left.getDefectiveProductsPercentage() - right.getDefectiveProductsPercentage() <= EPSILON;
    }
}
