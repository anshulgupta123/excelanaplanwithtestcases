package com.anaplan.dataexcelmicroservice.repository;

import com.anaplan.dataexcelmicroservice.modal.Employee;
import com.anaplan.dataexcelmicroservice.modal.EmployeeData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EmployeeDataRepository extends JpaRepository<EmployeeData,Long> {
}
