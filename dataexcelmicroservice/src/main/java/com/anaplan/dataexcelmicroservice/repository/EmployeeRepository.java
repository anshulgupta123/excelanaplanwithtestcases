package com.anaplan.dataexcelmicroservice.repository;

import com.anaplan.dataexcelmicroservice.modal.Employee;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EmployeeRepository extends MongoRepository<Employee, String> {

    @Query(value="{ 'fileId' : ?0 }", fields="{ 'employeeCode' : 1, 'employeeName' : 1}")
    List<Employee> findByFileId(Long fileId);
}
