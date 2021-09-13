package com.home.urix.PayRoll.Model;

import com.home.urix.PayRoll.Model.AllocationSchema.AllocationSchema;
import com.home.urix.PayRoll.Model.AllocationSchema.ApportionmentAllocation;
import com.home.urix.PayRoll.Model.AllocationSchema.FlatAllocation;
import com.home.urix.PayRoll.Model.Database.DataBase;
import com.home.urix.PayRoll.Model.Database.SQLiteDb;
import com.home.urix.PayRoll.Model.Departments.Organization;
import com.home.urix.PayRoll.Model.Departments.OrganizationStructure;
import com.home.urix.PayRoll.Model.Employee.Employee;
import com.home.urix.PayRoll.Model.calculationSchema.DepartmentCalculation;
import com.home.urix.PayRoll.Model.calculationSchema.OrganizationCalculation;
import com.home.urix.PayRoll.Model.calculationSchema.CalculationSchema;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.LinkedList;

public class MainModel {
    private final DataBase db;
    private AllocationSchema allocationSchema;
    private CalculationSchema calculationSchema;
    private final Organization organization;

    public MainModel(){
        db = new SQLiteDb();
        organization = new Organization();
        allocationSchema = new FlatAllocation();
        calculationSchema = new OrganizationCalculation(organization);
    }

    public void connectToDatabase(String dbName) throws SQLException, ClassNotFoundException {
        db.open(dbName);
    }

    public void calculate() {
        allocationSchema.calculate(calculationSchema.getOrganizationStructures());
    }

    public OrganizationStructure[] getCurrentModelDepartments(){
        return calculationSchema.getOrganizationStructures();
    }

    public void setFlatBalanceAllocation() {
        if(allocationSchema.getClass().getName().equals(FlatAllocation.class.getName())) return;
        allocationSchema = new FlatAllocation();
    }

    public void setApportionmentBalanceAllocation() {
        if(allocationSchema.getClass().getName().equals(ApportionmentAllocation.class.getName())) return;
        allocationSchema = new ApportionmentAllocation();
    }

    public void setDepartmentBalanceAllocation() {
        if(calculationSchema.getClass().getName().equals(DepartmentCalculation.class.getName())) return;
        calculationSchema = new DepartmentCalculation(organization);
    }

    public void setOrganizationBalanceAllocation() {
        if(calculationSchema.getClass().getName().equals(OrganizationCalculation.class.getName())) return;
        calculationSchema = new OrganizationCalculation(organization);
    }

    public void addDepartment(String departmentName){
        organization.addDepartment(departmentName);
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

    public LinkedList<Employee> allUsers(){
        return organization.employees();
    }
}
