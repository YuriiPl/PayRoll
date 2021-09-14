package com.home.urix.PayRoll.Model.AllocationSchema;

import com.home.urix.PayRoll.Model.Departments.OrganizationStructure;
import com.home.urix.PayRoll.Model.Employee.Employee;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.LinkedList;


public abstract class FundAllocationSchema {

    public void calculate(OrganizationStructure[] organizationStructures) throws AllocationException {
        for(OrganizationStructure struct : organizationStructures) {
            LinkedList<Employee> employees = struct.employees();
            BigDecimal fundAmount = struct.getFundAmount();
            BigDecimal salarySum = BigDecimal.valueOf(0);
            for (Employee e : employees) {
                salarySum = salarySum.add(BigDecimal.valueOf(e.getSalary()));
                if(e.getBirthDay().getMonth().equals(LocalDate.now().getMonth())){
                    e.setCacheBonus(e.getSalary()/10);
                } else {
                    e.setCacheBonus(0);
                }
            }
            if(salarySum.compareTo(fundAmount)>=0){
                throw new AllocationException();
            }

            calculation(employees,fundAmount.subtract(salarySum),salarySum);
        }
    }

    abstract protected void calculation(LinkedList<Employee> employees, BigDecimal fundAmount, BigDecimal salarySum);

    public abstract FundAllocationType type();
}
