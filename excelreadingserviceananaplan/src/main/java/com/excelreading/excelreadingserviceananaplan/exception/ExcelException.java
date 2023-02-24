package com.excelreading.excelreadingserviceananaplan.exception;

public class ExcelException extends RuntimeException {

    private String message;

    public ExcelException(String message) {
        super(message);

    }

}
