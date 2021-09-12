package com.home.urix.PayRoll.Model;

import com.home.urix.PayRoll.Model.AllocationSchema.AllocationSchema;
import com.home.urix.PayRoll.Model.AllocationSchema.ApportionmentAllocation;
import com.home.urix.PayRoll.Model.AllocationSchema.FlatAllocation;
import com.home.urix.PayRoll.Model.Database.DataBase;
import com.home.urix.PayRoll.Model.Database.SQLiteDb;
import com.home.urix.PayRoll.Model.Departments.Organization;
import com.home.urix.PayRoll.Model.calculationSchema.DepartmentCalculation;
import com.home.urix.PayRoll.Model.calculationSchema.OrganizationCalculation;
import com.home.urix.PayRoll.Model.calculationSchema.CalculationSchema;

import java.sql.SQLException;

public class MainModel {
    private final DataBase db;
    AllocationSchema allocationSchema;
    CalculationSchema calculationSchema;
    Organization organization;

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

    public CalculationSchema getCalculationSchema() {
        return calculationSchema;
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
}
