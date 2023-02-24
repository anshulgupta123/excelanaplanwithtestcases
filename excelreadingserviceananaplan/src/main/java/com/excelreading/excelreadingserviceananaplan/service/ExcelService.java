package com.excelreading.excelreadingserviceananaplan.service;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public interface ExcelService {
    public Object readingAndSavingExcelData(MultipartFile multipartFile);
}
