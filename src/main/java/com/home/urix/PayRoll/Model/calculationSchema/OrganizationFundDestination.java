package com.home.urix.PayRoll.Model.calculationSchema;

import com.home.urix.PayRoll.Model.Departments.Organization;
import com.home.urix.PayRoll.Model.Departments.OrganizationStructure;

public class OrganizationFundDestination implements FundDestinationSchema {
    Organization organization;
    public OrganizationFundDestination(Organization organization){
        this.organization=organization;
    }

    @Override
    public OrganizationStructure[] getOrganizationStructures() {
        return new OrganizationStructure[]{organization};
    }

    @Override
    public FundDestinationType type() {
        return FundDestinationType.ORGANIZATION;
    }
}
