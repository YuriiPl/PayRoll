package com.home.urix.PayRoll.Model.calculationSchema;

import com.home.urix.PayRoll.Model.Departments.OrganizationStructure;
import com.home.urix.PayRoll.View.BalanceAllocationPlaceEnum;

public interface FundDestinationSchema {
    OrganizationStructure[] getOrganizationStructures();
    FundDestinationType type();
}
