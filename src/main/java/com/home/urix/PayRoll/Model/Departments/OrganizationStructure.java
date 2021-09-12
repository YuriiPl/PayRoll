package com.home.urix.PayRoll.Model.Departments;

import com.home.urix.PayRoll.Model.Employee.Employee;

import java.math.BigDecimal;
import java.util.LinkedList;

public interface structure {
    LinkedList<Employee> employees();
    BigDecimal getAmount();
    void setAmount(BigDecimal amount);
}
