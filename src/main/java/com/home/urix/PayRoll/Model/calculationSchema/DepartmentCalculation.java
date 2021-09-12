package com.home.urix.PayRoll.Model.calculationSchema;

import com.home.urix.PayRoll.Model.Departments.Organization;
import com.home.urix.PayRoll.Model.Departments.OrganizationStructure;

public class DepartmentCalculation implements CalculationSchema {
    Organization organization;
    public DepartmentCalculation(Organization organization){
        this.organization=organization;
    }

    @Override
    public OrganizationStructure[] getOrganizationStructures() {
        return organization.departments().toArray(new OrganizationStructure[0]);
    }
}
