package com.home.urix.PayRoll.Model.Departments;

import com.home.urix.PayRoll.Model.Employee.Employee;

import java.time.LocalDate;
import java.util.LinkedList;

public class Department extends OrganizationStructure {
    private final LinkedList<Employee> employeesList = new LinkedList<>();
    private long id;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Department(String name, long id){
        setName(name);
        setId(id);
    }

    public Employee fireEmployee(int pos){
        return employeesList.remove(pos);
    }

    @Override
    public LinkedList<Employee> employees() {
        return employeesList;
    }

    public void addNewEmployee(Employee employee){
        employeesList.add(employee);
    }

}
