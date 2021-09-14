package com.home.urix.PayRoll.Model.calculationSchema;

import com.home.urix.PayRoll.Model.Departments.Organization;
import com.home.urix.PayRoll.Model.Departments.OrganizationStructure;

public class DepartmentFundDestination implements FundDestinationSchema {
    Organization organization;
    public DepartmentFundDestination(Organization organization){
        this.organization=organization;
    }

    @Override
    public OrganizationStructure[] getOrganizationStructures() {
        return organization.departments().toArray(new OrganizationStructure[0]);
    }

    @Override
    public FundDestinationType type() {
        return FundDestinationType.DEPARTMENT;
    }
}
