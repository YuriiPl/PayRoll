package com.home.urix.PayRoll.Model.AllocationSchema;

import com.home.urix.PayRoll.Model.Departments.OrganizationStructure;
import com.home.urix.PayRoll.View.BalanceAllocationTypeEnum;


public interface FundAllocationSchema {
    void calculate(OrganizationStructure[] organizationStructures) throws AllocationException;
    BalanceAllocationType type();
}
