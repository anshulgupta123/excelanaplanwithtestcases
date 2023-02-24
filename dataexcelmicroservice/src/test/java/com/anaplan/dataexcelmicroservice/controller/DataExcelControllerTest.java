package com.anaplan.dataexcelmicroservice.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.lenient;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.List;

import com.anaplan.dataexcelmicroservice.dto.MigrateDto;
import com.anaplan.dataexcelmicroservice.dto.Response;
import com.anaplan.dataexcelmicroservice.modal.EmployeeData;
import com.anaplan.dataexcelmicroservice.modal.EmployeeDto;
import com.anaplan.dataexcelmicroservice.serviceimpl.DataExcelServiceImpl;
import com.anaplan.dataexcelmicroservice.utility.Constants;
import com.anaplan.dataexcelmicroservice.utility.UrlConstants;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@ExtendWith(MockitoExtension.class)
class DataExcelControllerTest {
    @Mock
    private Environment env;

    @Mock
    private DataExcelServiceImpl dataExcelServiceImpl;

    @InjectMocks
    private DataExcelController dataExcelController;

    @Autowired
    private MockMvc mockMvc;

    @BeforeEach
    void init() {
        mockMvc = MockMvcBuilders.standaloneSetup(dataExcelController).build();
    }

    @AfterEach
    void cleanUp() {
        mockMvc = null;
    }

    @Test
    @DisplayName("Testing migrate data for controller")
    public void testMigrateData() throws Exception {
        List<EmployeeData> dataList = new ArrayList<>();
        EmployeeData employerData = new EmployeeData();
        employerData.setEmployeeName("Aman");
        dataList.add(employerData);
        Response<Object> responseFromService = new Response<>(env.getProperty(Constants.SUCCESS_CODE), env.getProperty(Constants.DATA_MIGRATED_SUCCESSFULLY), dataList);
        when(dataExcelServiceImpl.migrateDataToEmployeeData(51l)).thenReturn(responseFromService);
        MigrateDto migrateDto = new MigrateDto();
        migrateDto.setFileId(51l);
        ResponseEntity<Object> responseFromController = dataExcelController.migrateData(migrateDto);
        assertEquals(HttpStatus.OK, responseFromController.getStatusCode());
        mockMvc.perform(post("/dataexcel/v1/migrateData").contentType(MediaType.APPLICATION_JSON)
                .content(jsontoString(migrateDto))).andExpect(status().isOk());
        verify(dataExcelServiceImpl, times(2)).migrateDataToEmployeeData(any());
    }

    private String jsontoString(final Object obj) {
        String result;
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            String jsonContent = objectMapper.writeValueAsString(obj);
            result = jsonContent;
        } catch (JsonProcessingException ex) {
            result = "error while converting to string";
        }
        return result;
    }

    @Test
    @DisplayName("Testing get All employees")
    void testingGetAllEmployees() throws Exception {
        List<EmployeeData> dataList = new ArrayList<>();
        EmployeeData employeeData = new EmployeeData();
        employeeData.setEmployeeName("Anshul");
        employeeData.setEmployeeCode("2526946");
        EmployeeData employeeData1 = new EmployeeData();
        employeeData1.setEmployeeName("Aman");
        employeeData1.setEmployeeCode("2526950");
        dataList.add(employeeData1);
        dataList.add(employeeData);
        Response<Object> responseFromServiceImpl = new Response<>(env.getProperty(Constants.SUCCESS_CODE), env.getProperty(Constants.EMPLOYEES_FETCHED_SUCCESSFULLY), dataList);
        when(dataExcelServiceImpl.getAllEmployeeData()).thenReturn(responseFromServiceImpl);
        ResponseEntity<Object> responseFromController = dataExcelController.getAllEmployeeData();
        assertEquals(HttpStatus.OK, responseFromController.getStatusCode());
        this.mockMvc.perform(get("/dataexcel/v1/getAllEmployees")).andExpect(status().isOk());
        verify(dataExcelServiceImpl, times(2)).getAllEmployeeData();
    }

    @Test
    @DisplayName("Testing get employee by id")
    void testingGetEmployeeById() throws Exception {
        EmployeeDto employeeData = new EmployeeDto();
        employeeData.setEmployeeName("Anshul");
        employeeData.setEmployeeCode("2526946");
        Response<Object> responseFromServiceImpl = new Response<>(env.getProperty(Constants.SUCCESS_CODE), env.getProperty(Constants.EMPLOYEE_FETCHED_SUCCESSFULLY), employeeData);
        when(dataExcelServiceImpl.getEmployeeDataById(161l)).thenReturn(responseFromServiceImpl);
        ResponseEntity<Object> responseFromController = dataExcelController.getEmployeeById(161l);
        assertEquals(HttpStatus.OK, responseFromController.getStatusCode());
        this.mockMvc.perform(get("/dataexcel/v1/getEmployeeById").param("employeeId", "161")).andExpect(status().isOk());
        verify(dataExcelServiceImpl, times(2)).getEmployeeDataById(any());
    }
}