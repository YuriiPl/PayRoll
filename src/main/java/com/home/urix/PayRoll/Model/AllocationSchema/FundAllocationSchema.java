package com.home.urix.PayRoll.Model.AllocationSchema;

import com.home.urix.PayRoll.Model.Departments.OrganizationStructure;
import com.home.urix.PayRoll.Model.Employee.Employee;
import com.home.urix.PayRoll.Model.Employee.EmployeeType;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.LinkedList;


public abstract class FundAllocationSchema {

    public void calculate(OrganizationStructure[] organizationStructures) throws AllocationException {
        for(OrganizationStructure struct : organizationStructures) {
            LinkedList<Employee> employees = struct.employees();
            BigDecimal fundAmount = struct.getFundAmount();
            BigDecimal salarySum = BigDecimal.valueOf(0);
            HashMap<Long,Long> bonusForManagers= new HashMap<>();
            for (Employee e : employees) {
                salarySum = salarySum.add(BigDecimal.valueOf(e.getSalary()));
                if(e.getBirthDay().getMonth().equals(LocalDate.now().getMonth())){
                    e.setCacheBonus(e.getSalary()/10);
                    salarySum = salarySum.add(BigDecimal.valueOf(e.getSalary()/10));
                } else {
                    e.setCacheBonus(0);
                }
                if(e.getManagerId()!=0){
                    bonusForManagers.put(e.getManagerId(),bonusForManagers.get(e.getManagerId())+e.getSalary()/2);
                    salarySum = salarySum.add(BigDecimal.valueOf(e.getSalary()/2));
                }
            }
            if(salarySum.compareTo(fundAmount)>=0){
                throw new AllocationException();
            }

            for (Employee e : employees) {
                if(e.getPositionType() == EmployeeType.MANAGER){
                    e.setCacheBonus(e.getCacheBonus()+bonusForManagers.get(e.getId()));
                }
            }

            calculation(employees,fundAmount.subtract(salarySum),salarySum);
        }
    }

    abstract protected void calculation(LinkedList<Employee> employees, BigDecimal fundAmount, BigDecimal salarySum);

    public abstract FundAllocationType type();
}
