package com.home.urix.PayRoll.Model.AllocationSchema;

import com.home.urix.PayRoll.Model.Employee.Employee;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.LinkedList;

public class FlatFundAllocationSchema extends FundAllocationSchema {
    @Override
    public FundAllocationType type() {
        return FundAllocationType.FLAT;
    }

    @Override
    public void calculation(LinkedList<Employee> employees, BigDecimal fundAmount, BigDecimal salarySum){
        BigDecimal oldR = BigDecimal.valueOf(0);
        BigDecimal R = BigDecimal.valueOf(0);
        BigDecimal step=fundAmount.divide(BigDecimal.valueOf(employees.size()), 0, RoundingMode.HALF_UP);
        for (Employee employee : employees) {
            R = R.add(step);
            employee.setBonus((R.subtract(oldR)).longValueExact());
            oldR = R;
        }
    }

}
