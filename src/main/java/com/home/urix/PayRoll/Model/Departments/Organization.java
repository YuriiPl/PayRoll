package com.home.urix.PayRoll.Model.Departments;
import com.home.urix.PayRoll.Model.Database.DataBase;
import com.home.urix.PayRoll.Model.Employee.Employee;
import com.home.urix.PayRoll.Model.Employee.EmployeeType;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.Optional;


public class Organization  extends OrganizationStructure {
    DataBase db;
    private final ArrayList<OrganizationStructure> departments = new ArrayList<>();

    public Organization(DataBase db){
        setName("");
        this.db=db;
    }

    public void loadFromDb(){
        departments.addAll(Arrays.asList(db.getAllDepartments()));
        loadAllEmployeeFromDb();
    }

    private void loadAllEmployeeFromDb(){
        Employee [] employees = db.getAllEmployees();
        for (Employee employee : employees){
            for(OrganizationStructure dep : departments){
                if(((Department)dep).getId()==employee.getDepartmentId()){
                    ((Department) dep).addNewEmployee(employee);
                    break;
                }
            }
        }
    }

    public ArrayList<OrganizationStructure> departments(){
        return departments;
    }

    public void addNewDepartment(String departmentName){
        Optional<OrganizationStructure> dep = departments.stream().filter(s -> s.getName().equals(departmentName)).findAny();
        if(dep.isPresent()){
            throw new IllegalArgumentException();
        }
        departments.add(db.createNewDepartment(departmentName));
    }

    public void removeDepartment(int departmentPosition){
        if(departments.get(departmentPosition).employees().size()!=0){
            throw new RuntimeException();
        }
        Department department=(Department) departments.remove(departmentPosition);
        db.removeDepartment(department);
    }

    public void changeDepartmentName(int pos, String newName){
        Department department = (Department) departments.get(pos);
        if(db.changeDepartmentName(department.getId(),newName)) {
            department.setName(newName);
        }
    }

    public void addNewEmployee(int departmentNumber, String firstName, String midName, String lastName, LocalDate birthDay, LocalDate startDate, long salary, EmployeeType employeeType, String positionName){
        Employee employee = new Employee(firstName,midName,lastName,birthDay,startDate,salary,employeeType,positionName);
        Department department = (Department)departments.get(departmentNumber);
        if(db.addEmployee(employee,department.getId())) {
            department.addNewEmployee(employee);
        }
    }


    @Override
    public LinkedList<Employee> employees() {
        LinkedList<Employee> employeesList = new LinkedList<>();
        for (OrganizationStructure department : departments) {
            employeesList.addAll(department.employees());
        }
        return employeesList;
    }


    public void fireEmployee(int employeePos, Department department) {
        Employee employee=department.fireEmployee(employeePos);
        db.fireEmployee(employee);
    }

    public void editEmployeesFirstName(int employeeIndex, Department department, String firstName) {
        Employee employee = department.employees().get(employeeIndex);
        if(db.editEmployeesFirstName(employee.getId(),firstName)) {
            employee.setFirstName(firstName);
        }
    }

    public void editEmployeesMiddleName(int employeeIndex, Department department, String midName) {
        Employee employee = department.employees().get(employeeIndex);
        if(db.editEmployeesMiddleName(employee.getId(),midName)) {
            employee.setMidName(midName);
        }
    }

    public void editEmployeesLastName(int employeeIndex, Department department, String lastName) {
        Employee employee = department.employees().get(employeeIndex);
        if(db.editEmployeesLastName(employee.getId(),lastName)) {
            employee.setLastName(lastName);
        }
    }

    public void editEmployeesBirthday(int employeeIndex, Department department, LocalDate birthDay) {
        Employee employee = department.employees().get(employeeIndex);
        if(db.editEmployeesBirthday(employee.getId(),birthDay)) {
            employee.setBirthDay(birthDay);
        }
    }

    public void editEmployeesStartWorkingDate(int employeeIndex, Department department, LocalDate startWorkingDate) {
        Employee employee = department.employees().get(employeeIndex);
        if(db.editEmployeesStartWorkingDate(employee.getId(),startWorkingDate)) {
            employee.setHiringDate(startWorkingDate);
        }
    }

    public void editEmployeesSalary(int employeeIndex, Department department, long salary) {
        Employee employee = department.employees().get(employeeIndex);
        if(db.editEmployeesSalary(employee.getId(),salary)) {
            employee.setSalary(salary);
        }
    }

    public void editEmployeesDepartment(int employeeIndex, Department department, int newDepartmentNumber) {
        Employee employee = department.employees().remove(employeeIndex);
        Department newDepartment = ((Department)departments.get(newDepartmentNumber));
        long newDepartmentId=newDepartment.getId();
        if(db.editEmployeesDepartment(employee.getId(),newDepartmentId)) {
            employee.setDepartmentId(newDepartmentId);
            newDepartment.addNewEmployee(employee);
        }
    }

    public void editEmployeesPositionName(int employeeIndex, Department department, String positionName){
        Employee employee = department.employees().get(employeeIndex);
        if(db.editEmployeesPositionName(employee.getId(),positionName)) {
            employee.setPositionName(positionName);
        }
    }

    public void editEmployeesPositionType(int employeeIndex, Department department, EmployeeType employeeType){
        Employee employee = department.employees().get(employeeIndex);
        if(db.editEmployeesPositionType(employee.getId(),employeeType)) {
            employee.setPositionType(employeeType);
        }
    }

    public void editEmployeesManager(int employeeIndex, Department department, int managerIndex){
        Employee employee = department.employees().get(employeeIndex);
        Employee manager = department.employees().get(managerIndex);
        if(db.editEmployeesManager(employee.getId(),manager.getId())) {
            employee.setManager(manager);
        }
    }

}
