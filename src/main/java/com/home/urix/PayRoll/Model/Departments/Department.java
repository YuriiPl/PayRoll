package com.home.urix.PayRoll.Model.Departments;

import com.home.urix.PayRoll.Model.Employee.Employee;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.LinkedList;

public class Department extends OrganizationStructure {
    private final LinkedList<Employee> employeesList = new LinkedList<>();
    private int id;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Department(String name){
        setName(name);
    }

//    void hireEmployee(Employee employee){
//        employeesList.add(employee);
//    }

    void fireEmployee(Employee employee){
        employeesList.remove(employee);
    }

    @Override
    public LinkedList<Employee> employees() {
        return employeesList;
    }

    public void addNewEmployee(String firstName, String midName, String lastName, LocalDate birthDay, LocalDate startDate, long salary){
        employeesList.add(new Employee(firstName,midName,lastName,birthDay,startDate,salary));
    }

}
