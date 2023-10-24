package edu.vgtu.project.controller;

import edu.vgtu.project.dto.ComplaintDto;
import edu.vgtu.project.dto.ComplaintFullDto;
import edu.vgtu.project.service.ComplaintService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/complaint")
@RequiredArgsConstructor
public class ComplaintController {
    private final ComplaintService complaintService;

    @GetMapping("/{id}")
    public ComplaintFullDto getComplaint(@PathVariable Long id) {
        log.info("Получение по идентификатору: {}", id);
        ComplaintFullDto complaintFullDto = complaintService.find(id);
        log.info("Данные жалобы: {}", complaintFullDto);
        return complaintFullDto;
    }

    @GetMapping("/worker/{workerId}")
    public List<ComplaintDto> findComplaintsByWorkerId(@PathVariable Long workerId) {
        log.info("Получение списка по идентификатору работника: {}", workerId);
        List<ComplaintDto> allByWorkerId = complaintService.findAllByWorkerId(workerId);
        log.info("Найдено жалоб: {}", allByWorkerId.size());
        return allByWorkerId;
    }

    @PostMapping
    public Long createComplaint(ComplaintFullDto complaint) {
        log.info("Сохранение жалобы: {}", complaint);
        Long complaintId = complaintService.create(complaint);
        log.info("Жалоба сохранена с идентифкатором: {}", complaintId);
        return complaintId;
    }

    @PutMapping
    public void updateComplaint(ComplaintFullDto complaint) {
        log.info("Обновление жалобы: {}", complaint);
        complaintService.update(complaint);
        log.info("Данные жалобы обновлены");
    }
}
