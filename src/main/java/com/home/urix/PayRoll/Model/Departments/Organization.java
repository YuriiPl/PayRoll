package com.home.urix.PayRoll.Model.Departments;
import com.home.urix.PayRoll.Model.Employee.Employee;

import java.util.ArrayList;
import java.util.LinkedList;


public class Organization  extends OrganizationStructure {

    public Organization(){
        setName("");
    }
    
    private final ArrayList<OrganizationStructure> departments = new ArrayList<>();

    public ArrayList<OrganizationStructure> departments(){
        return departments;
    }

    public void addDepartment(Department department){
        departments.add(department);
    }

    @Override
    public LinkedList<Employee> employees() {
        LinkedList<Employee> employeesList = new LinkedList<>();
        for (OrganizationStructure department : departments) {
            employeesList.addAll(department.employees());
        }
        return employeesList;
    }


}
