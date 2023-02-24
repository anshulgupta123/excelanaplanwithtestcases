package com.anaplan.dataexcelmicroservice.dto;

import java.io.Serializable;

public class MigrateDto implements Serializable {

    private Long fileId;

    public Long getFileId() {
        return fileId;
    }

    public void setFileId(Long fileId) {
        this.fileId = fileId;
    }

    @Override
    public String toString() {
        return "MigrateDto{" +
                "fileId=" + fileId +
                '}';
    }
}
