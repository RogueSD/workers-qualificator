package edu.vgtu.project.exception;

import lombok.Getter;

@Getter
public class BusinessException extends RuntimeException {
    private final String message;
    private final Integer code;

    public BusinessException(Integer code, String message, Throwable cause) {
        super(message, cause);

        this.code = code;
        this.message = message;
    }
}
