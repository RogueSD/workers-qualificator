package edu.vgtu.project.controller.advice;

import edu.vgtu.project.dto.error.ErrorMessageDto;
import edu.vgtu.project.exception.BusinessException;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ExceptionControllerAdvice {

    @ExceptionHandler(BusinessException.class)
    public ResponseEntity<ErrorMessageDto> handle(BusinessException exception) {
        return ResponseEntity.status(exception.getCode())
                .body(
                        ErrorMessageDto.builder()
                                .message(exception.getMessage())
                                .code(exception.getCode())
                                .build()
                );
    }

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<ErrorMessageDto> handle(RuntimeException exception) {
        return ResponseEntity.status(500)
                .body(
                        ErrorMessageDto.builder()
                                .code(500)
                                .message("Произошла ошибка при обработке запроса: " + ExceptionUtils.getRootCauseMessage(exception))
                                .build()
                );
    }
}
