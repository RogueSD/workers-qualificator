package edu.vgtu.project.service;

import edu.vgtu.project.dto.rows.WorkerRowDto;
import edu.vgtu.project.exception.BusinessException;
import edu.vgtu.project.mapper.WorkerMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.lang.reflect.Field;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class IntegrationService {

    private final WorkerService workerService;
    private final WorkerMapper workerMapper;

    public ByteArrayResource getReport() {
        try {
            XSSFWorkbook workbook = new XSSFWorkbook();
            createAndFillWorkersSheet(workbook);
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            workbook.write(outputStream);
            return new ByteArrayResource(outputStream.toByteArray());
        } catch (Exception e) {
            throw new BusinessException(500, e.getMessage(), e.getCause());
        }
    }


    private void createAndFillWorkersSheet(XSSFWorkbook workbook) throws IllegalAccessException {
        Sheet sheet = workbook.createSheet("Workers");
        Field[] fields = WorkerRowDto.class.getDeclaredFields();
        Row header = sheet.createRow(0);
        for(int i = 0; i < fields.length; i++) {
            fields[i].setAccessible(true);
            Cell headerCell = header.createCell(i);
            headerCell.setCellValue(fields[i].getName());
            log.info(fields[i].getName());
        }

        List<WorkerRowDto> objects = workerService.getAllWorkers().stream().map(workerMapper::toRowDto).toList();
        for (int i = 0; i < objects.size(); i++) {
            Object item = objects.get(i);
            Row row = sheet.createRow(i+1);
            for(int j = 0; j < fields.length; j++) {
                Cell cell = row.createCell(j);
                cell.setCellValue(fields[j].get(item).toString());
            }
        }
    }
}