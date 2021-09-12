package com.home.urix.PayRoll.Model.calculationSchema;

import com.home.urix.PayRoll.Model.Departments.Organization;
import com.home.urix.PayRoll.Model.Departments.OrganizationStructure;

public class OrganizationCalculation implements CalculationSchema {
    Organization organization;
    public OrganizationCalculation(Organization organization){
        this.organization=organization;
    }

    @Override
    public OrganizationStructure[] getOrganizationStructures() {
        return new OrganizationStructure[]{organization};
    }
}
