package com.anaplan.dataexcelmicroservice.serviceimpl;

import com.anaplan.dataexcelmicroservice.dto.Response;
import com.anaplan.dataexcelmicroservice.exception.DataExcelException;
import com.anaplan.dataexcelmicroservice.modal.Employee;
import com.anaplan.dataexcelmicroservice.modal.EmployeeData;
import com.anaplan.dataexcelmicroservice.modal.EmployeeDto;
import com.anaplan.dataexcelmicroservice.repository.EmployeeDataRepository;
import com.anaplan.dataexcelmicroservice.repository.EmployeeRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class DataExcelServiceImplTest {

    @Mock
    private Environment environment;

    @Mock
    private EmployeeDataRepository employeeDataRepository;

    @InjectMocks
    private DataExcelServiceImpl dataExcelServiceImpl;

    @Mock
    private EmployeeRepository employeeRepository;


    @Test
    @DisplayName("Testing get employee data by id")
    public void testGetEmployeeById() {
        EmployeeData employeeData = new EmployeeData();
        employeeData.setEmployeeName("Anshul");
        employeeData.setEmployeeCode("2526946");
        when(employeeDataRepository.findById(1l)).thenReturn(Optional.of(employeeData));
        Object employee = dataExcelServiceImpl.getEmployeeDataById(1l);
        Response object = (Response) dataExcelServiceImpl.getEmployeeDataById(1l);
        EmployeeDto employerResponseDto = (EmployeeDto) object.getData();
        assertEquals("Anshul", employerResponseDto.getEmployeeName());

    }

    @Test
    @DisplayName("Testing get employee data by id for employee does not exist")
    public void testGetEmployeeByIdForEmployeeDoesNotExist() {
        Optional<EmployeeData> employeeData = null;
        when(employeeDataRepository.findById(109l)).thenReturn(employeeData);
        assertThrows(DataExcelException.class, () -> dataExcelServiceImpl.getEmployeeDataById(100l));
    }

    @Test
    @DisplayName("Testing get all employee data")
    public void testGetAllEmployees() {
        List<EmployeeData> dataList = new ArrayList<>();
        EmployeeData employeeData = new EmployeeData();
        employeeData.setEmployeeName("Anshul");
        employeeData.setEmployeeCode("2526946");
        EmployeeData employeeData1 = new EmployeeData();
        employeeData1.setEmployeeName("Anshul");
        employeeData1.setEmployeeCode("2526946");
        dataList.add(employeeData1);
        dataList.add(employeeData);
        when(employeeDataRepository.findAll()).thenReturn(dataList);
        Object employee = dataExcelServiceImpl.getAllEmployeeData();
        Response object = (Response) dataExcelServiceImpl.getAllEmployeeData();
        List<EmployeeDto> employerResponseList = (List<EmployeeDto>) object.getData();
        assertEquals(2, employerResponseList.size());
    }

    @Test
    @DisplayName("Testing migrate employee data")
    public void testMigrateData() {
        List<EmployeeData> dataList = new ArrayList<>();
        EmployeeData employerData = new EmployeeData();
        employerData.setEmployeeName("Aman");
        dataList.add(employerData);
        List<Employee> employeeList = new ArrayList<>();
        Employee employee = new Employee();
        employee.setEmployeeName("Aman");
        employee.setEmployeeCode("242452");
        employeeList.add(employee);
        when(employeeDataRepository.saveAll(any((((List.class)))))).thenReturn(dataList);
        when(employeeRepository.findByFileId(52l)).thenReturn(employeeList);
        Response response = (Response) dataExcelServiceImpl.migrateDataToEmployeeData(52l);
        assertEquals(1, ((List<EmployeeData>) response.getData()).size());
    }
}