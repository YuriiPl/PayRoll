package com.home.urix.PayRoll.Model.AllocationSchema;

import com.home.urix.PayRoll.Model.Departments.OrganizationStructure;
import com.home.urix.PayRoll.Model.Employee.Employee;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.LinkedList;

public class ApportionmentFundAllocationSchema extends FundAllocationSchema {
    @Override
    public FundAllocationType type() {
        return FundAllocationType.APPORTIONMENT;
    }

    @Override
    public void calculation(LinkedList<Employee> employees, BigDecimal fundAmount, BigDecimal salarySum){
        BigDecimal oldR = BigDecimal.valueOf(0);
        BigDecimal R = BigDecimal.valueOf(0);
        for (Employee employee : employees) {
            R = R.add(fundAmount.multiply(BigDecimal.valueOf(employee.getSalary())).divide(salarySum, 0, RoundingMode.HALF_UP));
            employee.setBonus((R.subtract(oldR)).longValueExact());
            oldR = R;
        }
    }

}
