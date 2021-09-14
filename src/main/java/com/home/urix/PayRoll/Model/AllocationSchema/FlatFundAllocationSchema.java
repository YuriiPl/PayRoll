package com.home.urix.PayRoll.Model.AllocationSchema;

import com.home.urix.PayRoll.Model.Departments.OrganizationStructure;
import com.home.urix.PayRoll.Model.Employee.Employee;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.LinkedList;

public class FlatFundAllocationSchema implements FundAllocationSchema {

    @Override
    public void calculate(OrganizationStructure[] organizationStructures) throws AllocationException {
        for(OrganizationStructure struct : organizationStructures) {
            LinkedList<Employee> employees = struct.employees();
            BigDecimal fundAmount = struct.getFundAmount();
            BigDecimal oldR = BigDecimal.valueOf(0);
            BigDecimal R = BigDecimal.valueOf(0);
            BigDecimal salarySum = BigDecimal.valueOf(0);
            for (Employee e : employees) {
                salarySum = salarySum.add(BigDecimal.valueOf(e.getSalary()));
                e.setBonus(0);
            }
            if(salarySum.compareTo(fundAmount)>=0){
                throw new AllocationException();
            }
            fundAmount=fundAmount.subtract(salarySum);
            BigDecimal step=fundAmount.divide(BigDecimal.valueOf(employees.size()), 0, RoundingMode.HALF_UP);
            for (Employee employee : employees) {
                R = R.add(step);
                employee.setBonus((R.subtract(oldR)).longValueExact());
                oldR = R;
            }
        }
    }

    @Override
    public BalanceAllocationType type() {
        return BalanceAllocationType.FLAT;
    }

}
