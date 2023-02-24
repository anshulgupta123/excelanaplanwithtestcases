package com.anaplan.dataexcelmicroservice.controller;

import com.anaplan.dataexcelmicroservice.dto.MigrateDto;
import com.anaplan.dataexcelmicroservice.service.DataExcelService;
import com.anaplan.dataexcelmicroservice.utility.UrlConstants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/dataexcel")
@RestController
public class DataExcelController {

    Logger logger = LoggerFactory.getLogger(DataExcelController.class);

    @Autowired
    DataExcelService dataExcelService;

    @PostMapping(UrlConstants.MIGRATE_DATA)
    public ResponseEntity<Object> migrateData(@RequestBody MigrateDto migrateDto) {
        logger.info("Request for migrateData of DataExcelController");
        return new ResponseEntity<>(dataExcelService.migrateDataToEmployeeData(migrateDto.getFileId()), HttpStatus.OK);
    }

    @GetMapping(UrlConstants.GET_EMPLOYEE_DATA_BY_ID)
    public ResponseEntity<Object> getEmployeeById(@RequestParam Long employeeId) {
        logger.info("Request for getEmployeeById of DataExcelController");
        return new ResponseEntity<>(dataExcelService.getEmployeeDataById(employeeId), HttpStatus.OK);
    }

    @GetMapping(UrlConstants.GET_ALL_EMPLOYEE)
    public ResponseEntity<Object> getAllEmployeeData() {
        logger.info("Request for getAllEmployeeData of DataExcelController");
        return new ResponseEntity<>(dataExcelService.getAllEmployeeData(), HttpStatus.OK);
    }

}
