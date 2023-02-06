package com.calife.autorizador.services.exceptions;

import org.springframework.http.HttpStatus;

public class ValidatorException extends RuntimeException{

    private String errorMessage;
    private HttpStatus httpStatus;
    private int codigoDoErro;

    public ValidatorException(String errorMessage, HttpStatus httpStatus) {
        super(errorMessage);
        this.errorMessage = errorMessage;
        this.httpStatus = httpStatus;
    }

    public ValidatorException(String errorMessage, int codigoDoErro) {
        super(errorMessage);
        this.errorMessage = errorMessage;
        this.codigoDoErro = codigoDoErro;
    }
}
