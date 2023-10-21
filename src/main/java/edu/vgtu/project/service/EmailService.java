package edu.vgtu.project.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import edu.vgtu.project.entity.Worker;

@Slf4j
@Service
@RequiredArgsConstructor
public class EmailService {
    public final JavaMailSender emailSender;

     public void notifyQualificationUpdate(Worker worker, String fromAddress, String toAddress)
     {
            String subject = "Изменение квалификации рабочего";
            String message = "Рабочий "+worker.getSurname()+" "+worker.getName()+" достиг квалификации "+worker.getQualification();
            this.sendTextEmail(fromAddress, toAddress, subject, message);
     }

    public void sendTextEmail(String fromAddress, String toAddress, String subject, String message) {
        SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
        simpleMailMessage.setFrom(fromAddress);
        simpleMailMessage.setTo(toAddress);
        simpleMailMessage.setSubject(subject);
        simpleMailMessage.setText(message);
        emailSender.send(simpleMailMessage);
    }
}
