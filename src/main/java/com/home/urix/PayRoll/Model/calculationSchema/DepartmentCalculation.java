package com.home.urix.PayRoll.Model.TargetCalculation;

import com.home.urix.PayRoll.Model.Departments.Organization;

public class DepartmentCalculation implements CalculationSchemaInterface {
    Organization organization;
    public DepartmentCalculation(Organization organization){
        this.organization=organization;
    }

}
