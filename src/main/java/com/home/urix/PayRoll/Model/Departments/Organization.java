package com.home.urix.PayRoll.Model.Departments;
import com.home.urix.PayRoll.Model.Employee.Employee;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Optional;


public class Organization  extends OrganizationStructure {
    private final ArrayList<OrganizationStructure> departments = new ArrayList<>();

    public Organization(){
        setName("");
    }

    public ArrayList<OrganizationStructure> departments(){
        return departments;
    }

    public void addDepartment(String departmentName){
        Optional<OrganizationStructure> dep = departments.stream().filter(s -> s.getName().equals(departmentName)).findAny();
        if(dep.isPresent()){
            throw new IllegalArgumentException();
        }
        departments.add(new Department(departmentName));
    }

    public void removeDepartment(int departmentPosition){
        if(departments.get(departmentPosition).employees().size()!=0){
            throw new RuntimeException();
        }
        departments.remove(departmentPosition);
    }

    public void changeDepartmentName(int pos, String newName){
        departments.get(pos).setName(newName);
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
