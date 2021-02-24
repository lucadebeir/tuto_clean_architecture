package com.clean.architecture.tuto.rest.models;

import java.util.ArrayList;
import java.util.List;

public class ResponseApi<T> {

    private T result;

    private List<String> errors;

    public T getResult() {
        return result;
    }

    public void setResult(T result) {
        this.result = result;
    }

    public List<String> getErrors() {
        return errors;
    }

    public void setErrors(List<String> errors) {
        this.errors = errors;
    }

    public ResponseApi() {
        this.errors = new ArrayList<>();
    }

    public ResponseApi(T result) {
        this.result = result;
        this.errors = new ArrayList<>();
    }

    public ResponseApi(List<String> errors) {
        this.errors = errors;
    }
}
