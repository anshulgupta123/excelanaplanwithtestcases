package com.anaplan.dataexcelmicroservice.exception;

import com.anaplan.dataexcelmicroservice.service.DataExcelService;

public class DataExcelException extends RuntimeException {

    private String message;

    public DataExcelException(String message) {
        super(message);
        this.message = message;
    }

    @Override
    public String getMessage() {
        return message;
    }
}
