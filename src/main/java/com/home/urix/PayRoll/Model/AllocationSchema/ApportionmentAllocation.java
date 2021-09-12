package com.home.urix.PayRoll.Model.AllocationType;

import com.home.urix.PayRoll.Model.Departments.OrganizationStructure;
import com.home.urix.PayRoll.Model.Employee.Employee;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.LinkedList;

public class ApportionmentAllocation implements AllocationSchemaInterface {
    @Override
    public void calculate(OrganizationStructure department) {
        LinkedList<Employee> emploees=department.employees();

    }

    static long[] apportionmentCalculation(long[] mass, BigDecimal amount){
        BigDecimal oldR = BigDecimal.valueOf(0);
        BigDecimal R = BigDecimal.valueOf(0);
        long[] result = new long[mass.length];
        BigDecimal salarySum = BigDecimal.valueOf(0);
        for (long l : mass) {
            salarySum = salarySum.add(BigDecimal.valueOf(l));
        }
        for (int i = 0; i < mass.length; i++) {
            long l = mass[i];
            R = R.add(amount.multiply(BigDecimal.valueOf(l)).divide(salarySum, 0, RoundingMode.HALF_UP));
            result[i]=(R.subtract(oldR)).longValueExact();
            oldR = R;
        }
        return result;
    }
}
