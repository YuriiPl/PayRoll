package com.home.urix.PayRoll.Model.TargetCalculation;

import com.home.urix.PayRoll.Model.Departments.Organization;

public class OrganizationCalculation implements CalculationSchemaInterface {
    Organization organization;
    public OrganizationCalculation(Organization organization){
        this.organization=organization;
    }

}
