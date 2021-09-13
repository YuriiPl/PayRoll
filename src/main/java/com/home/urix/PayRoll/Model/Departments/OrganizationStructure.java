package com.home.urix.PayRoll.Model.Departments;

import com.home.urix.PayRoll.Model.Employee.Employee;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.LinkedList;

public abstract class OrganizationStructure {
    private String name;
    private BigDecimal amount;

    public abstract LinkedList<Employee> employees();

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
