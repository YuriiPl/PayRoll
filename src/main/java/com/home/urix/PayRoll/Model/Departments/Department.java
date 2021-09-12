package com.home.urix.PayRoll.Model.Departments;

import com.home.urix.PayRoll.Model.Employee.Employee;

import java.math.BigDecimal;
import java.util.LinkedList;

public class Department extends OrganizationStructure {
    private final LinkedList<Employee> employeesList = new LinkedList<>();

    void hireEmployee(Employee employee){
        employeesList.add(employee);
    }

    void fireEmployee(Employee employee){
        employeesList.remove(employee);
    }

    @Override
    public LinkedList<Employee> employees() {
        return employeesList;
    }

}
