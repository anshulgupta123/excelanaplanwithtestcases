package com.excelreading.excelreadingserviceananaplan.feign;


import com.excelreading.excelreadingserviceananaplan.dto.MigrateDto;
import com.excelreading.excelreadingserviceananaplan.utility.UrlConstants;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "dataexcelmicroservice", url = "http://localhost:8080")
public interface DataExcelServiceProxy {

    @PostMapping(UrlConstants.MIGRATE_DATA)
    public ResponseEntity<Object> migrateData(@RequestBody MigrateDto migrateDto);

}
