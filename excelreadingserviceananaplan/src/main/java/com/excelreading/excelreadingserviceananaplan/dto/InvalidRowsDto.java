package com.excelreading.excelreadingserviceananaplan.dto;

import java.io.Serializable;
import java.util.List;

public class InvalidRowsDto implements Serializable {
    private List<Integer> invalidDataRows;
    private Long fileId;

    public List<Integer> getInvalidDataRows() {
        return invalidDataRows;
    }

    public void setInvalidDataRows(List<Integer> invalidDataRows) {
        this.invalidDataRows = invalidDataRows;
    }

    public Long getFileId() {
        return fileId;
    }

    public void setFileId(Long fileId) {
        this.fileId = fileId;
    }
}
