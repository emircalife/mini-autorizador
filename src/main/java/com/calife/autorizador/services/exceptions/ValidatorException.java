package com.calife.autorizador.services.exceptions;

import org.springframework.http.HttpStatus;

public class ValidatorException extends RuntimeException{

    private String errorMessage;
    private HttpStatus httpStatus;

    public ValidatorException(String errorMessage, HttpStatus httpStatus) {
        super(errorMessage);
        this.errorMessage = errorMessage;
        this.httpStatus = httpStatus;
    }
}
