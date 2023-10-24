package edu.vgtu.project.service;

import edu.vgtu.project.entity.Worker;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.text.StringSubstitutor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@Service
@RequiredArgsConstructor
public class EmailService {
    private final static String template = """
            Рабочий ${lastName} ${firstName} достиг новой квалификации в профессии ${specialization}: ${qualification}.\s
            Его текущие параметры: \s
                Произведено продукции / выполнено работ = ${manufactured} (шт / работ);\s
                Процент брака = ${defected}%.
            """;
    private static final String SUBJECT = "Изменение квалификации рабочего";

    @Value("${spring.mail.username}")
    private String sender;
    private final JavaMailSender emailSender;


    public void notifyQualificationUpdate(Worker worker, String recipient) {
        log.info("Отправка сведений о работнике с идентификатором: {} на адрес электронной почты: {}", worker.getId(), recipient);
        sendTextEmail(recipient, StringSubstitutor.replace(template, prepareArgs(worker)));
    }

    private void sendTextEmail(String toAddress, String message) {
        SimpleMailMessage simpleMailMessage = new SimpleMailMessage();

        simpleMailMessage.setFrom(sender);
        simpleMailMessage.setSubject(SUBJECT);
        simpleMailMessage.setTo(toAddress);
        simpleMailMessage.setText(message);

        emailSender.send(simpleMailMessage);
    }

    private Map<String, String> prepareArgs(Worker worker) {
        double percentage = 0.0f;
        long manufactured = 0;

        if (worker.getManufacturedProductsCount() != null && worker.getManufacturedProductsCount() != 0) {
            percentage = ((double) worker.getDefectiveProductsCount() / (double) worker.getManufacturedProductsCount());
            manufactured = worker.getManufacturedProductsCount();
        }

        Map<String, String> result = new HashMap<>();

        result.put("lastName", worker.getSurname());
        result.put("firstName", worker.getName());
        result.put("specialization", worker.getQualification().getSpecialization().getName());
        result.put("qualification", worker.getQualification().getName());
        result.put("manufactured", Long.toString(manufactured));
        result.put("defected", Double.toString(percentage * 100));

        return result;
    }
}
