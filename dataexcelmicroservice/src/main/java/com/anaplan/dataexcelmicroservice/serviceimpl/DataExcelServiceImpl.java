package com.anaplan.dataexcelmicroservice.serviceimpl;

import com.anaplan.dataexcelmicroservice.dto.Response;
import com.anaplan.dataexcelmicroservice.exception.DataExcelException;
import com.anaplan.dataexcelmicroservice.modal.Employee;
import com.anaplan.dataexcelmicroservice.modal.EmployeeData;
import com.anaplan.dataexcelmicroservice.modal.EmployeeDto;
import com.anaplan.dataexcelmicroservice.repository.EmployeeDataRepository;
import com.anaplan.dataexcelmicroservice.repository.EmployeeRepository;
import com.anaplan.dataexcelmicroservice.service.DataExcelService;
import com.anaplan.dataexcelmicroservice.utility.Constants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class DataExcelServiceImpl implements DataExcelService {

    Logger logger = LoggerFactory.getLogger(DataExcelServiceImpl.class);

    @Autowired
    Environment env;

    @Autowired
    EmployeeRepository employeeRepository;

    @Autowired
    EmployeeDataRepository employeeDataRepository;

    @Override
    public Object migrateDataToEmployeeData(Long fileId) {
        logger.info("Inside migrateDataToEmployeeData of DataExcelServiceImpl");
        try {
            List<EmployeeData> savedData = employeeDataRepository.saveAll(populateEmployeeDataFromEmployee(getUnproceesedDataFromEmployee(fileId)));
            logger.info("Constnsts code is :{}", Constants.SUCCESS_CODE);
            logger.info("env is  is :{}", env);
            logger.info("Constnsts code is :{}", env.getProperty(Constants.SUCCESS_CODE));
            return new Response<>(env.getProperty(Constants.SUCCESS_CODE), env.getProperty(Constants.DATA_MIGRATED_SUCCESSFULLY), savedData);
        } catch (Exception e) {
            e.printStackTrace();
            String errorMessage = null;
            if (e instanceof DataExcelException) {
                errorMessage = ((DataExcelException) e).getMessage();
            } else {
                errorMessage = MessageFormat.format("Exception caught in migrateDataToEmployeeData of DataExcelServiceImpl:{0}", e.getMessage());
            }
            logger.error(errorMessage);
            throw new DataExcelException(errorMessage);
        }
    }

    @Override
    public Object getEmployeeDataById(Long employeeId) {
        logger.info("Inside getEmployeeDataById of DataExcelServiceImpl");
        try {
            Optional<EmployeeData> employeeData = employeeDataRepository.findById(employeeId);
            if (!employeeData.isPresent()) {
                throw new DataExcelException(env.getProperty(Constants.EMPLOYEE_DOES_NOT_EXIST));
            }
            EmployeeData employee = employeeData.get();
            return new Response<>(env.getProperty(Constants.SUCCESS_CODE), env.getProperty(Constants.EMPLOYEE_FETCHED_SUCCESSFULLY), populateEmployeeDtoFromEmployeeData(employee));
        } catch (Exception e) {
            e.printStackTrace();
            String errorMessage = null;
            if (e instanceof DataExcelException) {
                errorMessage = ((DataExcelException) e).getMessage();
            } else {
                errorMessage = MessageFormat.format("Exception caught in getEmployeeDataById of DataExcelServiceImpl:{0}", e.getMessage());
            }
            logger.error(errorMessage);
            throw new DataExcelException(errorMessage);
        }
    }

    @Override
    public Object getAllEmployeeData() {
        logger.info("Inside getAllEmployeeData of DataExcelServiceImpl");
        try {
            List<EmployeeDto> responseDataList = new ArrayList<>();
            List<EmployeeData> dataFromRepo = employeeDataRepository.findAll();
            for (EmployeeData employeeData : dataFromRepo) {
                responseDataList.add(populateEmployeeDtoFromEmployeeData(employeeData));
            }
            return new Response<>(env.getProperty(Constants.SUCCESS_CODE), env.getProperty(Constants.EMPLOYEES_FETCHED_SUCCESSFULLY), responseDataList);
        } catch (Exception e) {
            e.printStackTrace();
            String errorMessage = null;
            if (e instanceof DataExcelException) {
                errorMessage = ((DataExcelException) e).getMessage();
            } else {
                errorMessage = MessageFormat.format("Exception caught in getAllEmployeeData of DataExcelServiceImpl:{0}", e.getMessage());
            }
            logger.error(errorMessage);
            throw new DataExcelException(errorMessage);
        }
    }

    public List<Employee> getUnproceesedDataFromEmployee(Long fileId) throws Exception {
        logger.info("Inside getUnproceesedDataFromEmployee of DataExcelServiceImpl");
        List<Employee> employeeData = employeeRepository.findByFileId(fileId);
        logger.info("Data from repo is :{}", employeeData);
        return employeeData;
    }

    public List<EmployeeData> populateEmployeeDataFromEmployee(List<Employee> employeeData) throws Exception {
        logger.info("Inside getUnproceesedDataFromEmployee of DataExcelServiceImpl");
        List<EmployeeData> employeeDataList = new ArrayList<>();
        for (Employee employeeProjection : employeeData) {
            EmployeeData employee = new EmployeeData();
            employee.setEmployeeCode(employeeProjection.getEmployeeCode());
            employee.setEmployeeName(employeeProjection.getEmployeeName());
            employeeDataList.add(employee);
        }
        return employeeDataList;
    }


    public EmployeeDto populateEmployeeDtoFromEmployeeData(EmployeeData employee) throws Exception {
        logger.info("Inside populateEmployeeDtoFromEmployeeData of DataExcelServiceImpl");
        EmployeeDto employeeDto = new EmployeeDto();
        employeeDto.setEmployeeCode(employee.getEmployeeCode());
        employeeDto.setEmployeeId(employee.getId());
        employeeDto.setEmployeeName(employee.getEmployeeName());
        return employeeDto;
    }

}



