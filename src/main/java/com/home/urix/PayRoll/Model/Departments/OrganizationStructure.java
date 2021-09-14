package com.home.urix.PayRoll.Model.Departments;

import com.home.urix.PayRoll.Model.Employee.Employee;
import com.home.urix.PayRoll.Model.Employee.EmployeeType;

import java.math.BigDecimal;
import java.util.LinkedList;

public abstract class OrganizationStructure {
    private String name;
    private BigDecimal fundAmount=BigDecimal.valueOf(0);

    public abstract LinkedList<Employee> employees();

    public BigDecimal getFundAmount() {
        return fundAmount;
    }

    public void setFundAmount(BigDecimal fundAmount) {
        this.fundAmount = fundAmount;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
