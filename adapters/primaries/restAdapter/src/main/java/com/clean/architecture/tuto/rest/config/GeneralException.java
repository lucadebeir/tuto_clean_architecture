package com.clean.architecture.tuto.rest.config;

import com.clean.architecture.tuto.core.exceptions.BusinessException;
import com.clean.architecture.tuto.rest.models.ResponseApi;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.Collections;

@ControllerAdvice
public class GeneralException
        extends ResponseEntityExceptionHandler {

    private final String ERROR = "Erreur technique : Veuillez contacter le support technique";

    @ExceptionHandler(Exception.class)
    protected ResponseEntity<?> handleCleanException(Exception e) {

        if (e instanceof BusinessException) {
            return new ResponseEntity<>(new ResponseApi<>(((BusinessException) e).getErrorsList()),  HttpStatus.BAD_REQUEST);
        } else {
            return new ResponseEntity<>(new ResponseApi<>(Collections.singletonList(ERROR)), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}