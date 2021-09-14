package com.home.urix.PayRoll.Model;

import com.home.urix.PayRoll.Model.AllocationSchema.*;
import com.home.urix.PayRoll.Model.Database.DataBase;
import com.home.urix.PayRoll.Model.Database.SQLiteDb;
import com.home.urix.PayRoll.Model.Departments.Department;
import com.home.urix.PayRoll.Model.Departments.Organization;
import com.home.urix.PayRoll.Model.Departments.OrganizationStructure;
import com.home.urix.PayRoll.Model.calculationSchema.DepartmentFundDestination;
import com.home.urix.PayRoll.Model.calculationSchema.FundDestinationType;
import com.home.urix.PayRoll.Model.calculationSchema.OrganizationFundDestination;
import com.home.urix.PayRoll.Model.calculationSchema.FundDestinationSchema;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;

public class MainModel {
    private final DataBase db;
    private FundAllocationSchema fundAllocationchema;
    private FundDestinationSchema fundDestinationSchema;
    private final Organization organization;

    public MainModel(){
        db = new SQLiteDb();
        organization = new Organization(db);
        fundAllocationchema = new FlatFundAllocationSchema();
        fundDestinationSchema = new OrganizationFundDestination(organization);
    }

    public void connectToDatabase(String dbName) throws SQLException, ClassNotFoundException {
        db.open(dbName);
    }

    public void calculate() throws AllocationException {
        fundAllocationchema.calculate(fundDestinationSchema.getOrganizationStructures());
    }

    public OrganizationStructure[] getCurrentModelDepartments(){
        return fundDestinationSchema.getOrganizationStructures();
    }

    public void setFlatBalanceAllocation() {
        if(fundAllocationchema.getClass().getName().equals(FlatFundAllocationSchema.class.getName())) return;
        fundAllocationchema = new FlatFundAllocationSchema();
    }

    public void setApportionmentBalanceAllocation() {
        if(fundAllocationchema.getClass().getName().equals(ApportionmentFundAllocationSchema.class.getName())) return;
        fundAllocationchema = new ApportionmentFundAllocationSchema();
    }

    public void setDepartmentBalanceAllocation() {
        if(fundDestinationSchema.getClass().getName().equals(DepartmentFundDestination.class.getName())) return;
        fundDestinationSchema = new DepartmentFundDestination(organization);
    }

    public void setOrganizationBalanceAllocation() {
        if(fundDestinationSchema.getClass().getName().equals(OrganizationFundDestination.class.getName())) return;
        fundDestinationSchema = new OrganizationFundDestination(organization);
    }

    public void addDepartment(String departmentName){
        organization.addNewDepartment(departmentName);
    }

    public ArrayList<OrganizationStructure> departments(){
        return organization.departments();
    }

    public void removeDepartment(int departmentPosition) {
        organization.removeDepartment(departmentPosition);
    }

    public void changeDepartmentName(int departmentPosition, String newName) {
        organization.changeDepartmentName(departmentPosition,newName);
    }

    public void addNewEmployee(int departmentNumber, String firstName, String midName, String lastName, LocalDate birthDay, LocalDate startDate, long salary) {
        organization.addNewEmployee(departmentNumber,firstName,midName,lastName,birthDay,startDate,salary);
    }

    public void loadData() {
        organization.loadFromDb();
    }

    public void fireEmployee(int employeePos, Department department) {
        organization.fireEmployee(employeePos,department);
    }

    public void editEmployeesFirstName(int employeeIndex, Department department, String firstName) {
        organization.editEmployeesFirstName(employeeIndex,department,firstName);
    }

    public void editEmployeesMiddleName(int employeeIndex, Department department, String midName) {
        organization.editEmployeesMiddleName(employeeIndex,department,midName);
    }

    public void editEmployeesLastName(int employeeIndex, Department department, String lastName) {
        organization.editEmployeesLastName(employeeIndex,department,lastName);
    }

    public void editEmployeesBirthday(int employeeIndex, Department department, LocalDate birthDay) {
        organization.editEmployeesBirthday(employeeIndex,department,birthDay);
    }

    public void editEmployeesStartWorkingDate(int employeeIndex, Department department, LocalDate startWorkingDate) {
        organization.editEmployeesStartWorkingDate(employeeIndex,department,startWorkingDate);
    }

    public void editEmployeesSalary(int employeeIndex, Department department, long salary) {
        organization.editEmployeesSalary(employeeIndex,department,salary);
    }

    public void editEmployeesDepartment(int employeeIndex, Department department, int newDepartmentNumber) {
        organization.editEmployeesDepartment(employeeIndex,department,newDepartmentNumber);
    }

    public int countOfEmployees() {
        return organization.employees().size();
    }

    public FundAllocationType fundAllocationSchemaType() {
        return fundAllocationchema.type();
    }

    public FundDestinationType fundDestinationSchemaType() {
        return fundDestinationSchema.type();
    }
}
