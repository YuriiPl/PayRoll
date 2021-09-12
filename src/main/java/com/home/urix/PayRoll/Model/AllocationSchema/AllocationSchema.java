package com.home.urix.PayRoll.Model.AllocationSchema;

import com.home.urix.PayRoll.Model.Departments.OrganizationStructure;

public interface AllocationSchema {
    void calculate(OrganizationStructure[] organizationStructures);
}
