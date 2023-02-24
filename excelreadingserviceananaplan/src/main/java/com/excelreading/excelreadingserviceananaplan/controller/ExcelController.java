package com.excelreading.excelreadingserviceananaplan.controller;

import com.excelreading.excelreadingserviceananaplan.service.ExcelService;
import com.excelreading.excelreadingserviceananaplan.utility.UrlConstants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/excelreading")
public class ExcelController {
    Logger logger = LoggerFactory.getLogger(ExcelController.class);

    @Autowired
    ExcelService excelService;

    @PostMapping(UrlConstants.READ_AND_SAVE_DATA)
    public ResponseEntity<Object> readExcel(@RequestParam("file") MultipartFile multipartFile) {
        logger.info("Request for readExcel of ExcelController");
        return new ResponseEntity<>(excelService.readingAndSavingExcelData(multipartFile), HttpStatus.OK);
    }


}
