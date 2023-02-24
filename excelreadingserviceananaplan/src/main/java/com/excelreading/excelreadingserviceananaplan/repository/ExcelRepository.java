package com.excelreading.excelreadingserviceananaplan.repository;

import com.excelreading.excelreadingserviceananaplan.modal.Employee;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ExcelRepository extends MongoRepository<Employee, String> {
}
