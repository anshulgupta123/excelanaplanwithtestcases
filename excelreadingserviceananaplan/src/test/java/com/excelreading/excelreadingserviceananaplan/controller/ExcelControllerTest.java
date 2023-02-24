package com.excelreading.excelreadingserviceananaplan.controller;

import com.excelreading.excelreadingserviceananaplan.serviceimpl.ExcelServiceImpl;
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
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
class ExcelControllerTest {

    @Mock
    private Environment env;

    @Mock
    private ExcelServiceImpl excelServiceImpl;

    @InjectMocks
    private ExcelController excelController;

    @Autowired
    private MockMvc mockMvc;

    @BeforeEach
    void init() {
        mockMvc = MockMvcBuilders.standaloneSetup(excelController).build();
    }

    @AfterEach
    void cleanUp() {
        mockMvc = null;
    }

    @Test
    @DisplayName("Testing readExcel  for controller")
    public void readExcel() throws Exception {
        MockMultipartFile file
                = new MockMultipartFile(
                "file",
                "hello.txt",
                MediaType.TEXT_PLAIN_VALUE,
                "Hello, World!".getBytes()
        );
        mockMvc.perform(multipart("/excelreading/v1/readAndSaveExcelData").file(file)).andExpect(status().isOk());
    }
}