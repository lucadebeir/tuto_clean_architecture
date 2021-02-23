package com.clean.architecture.tuto.core.exceptions;

import java.util.ArrayList;
import java.util.List;

public class BusinessException extends Exception {

    private List<String> errorsList;

    public BusinessException(String message) {
        super(message);
        this.errorsList = new ArrayList<>();
    }

    public BusinessException(String message, List<String> errorsList) {
        super(message);
        this.errorsList = errorsList;
    }

    public List<String> getErrorsList() {
        return errorsList;
    }

    public void setErrorsList(List<String> errorsList) {
        this.errorsList = errorsList;
    }
}
