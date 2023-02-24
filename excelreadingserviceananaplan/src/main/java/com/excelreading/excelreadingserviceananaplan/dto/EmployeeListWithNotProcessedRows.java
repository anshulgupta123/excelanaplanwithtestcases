package com.excelreading.excelreadingserviceananaplan.dto;

import com.excelreading.excelreadingserviceananaplan.modal.Employee;
import java.io.Serializable;
import java.util.List;

public class EmployeeListWithNotProcessedRows implements Serializable {

    private List<Employee> listOfEmployees;
    private List<Integer> notProcessedRows;

    public List<Employee> getListOfEmployees() {
        return listOfEmployees;
    }

    public void setListOfEmployees(List<Employee> listOfEmployees) {
        this.listOfEmployees = listOfEmployees;
    }

    public List<Integer> getNotProcessedRows() {
        return notProcessedRows;
    }

    public void setNotProcessedRows(List<Integer> notProcessedRows) {
        this.notProcessedRows = notProcessedRows;
    }
}
