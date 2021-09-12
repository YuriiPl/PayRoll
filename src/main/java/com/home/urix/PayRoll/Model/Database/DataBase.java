package com.home.urix.PayRoll.Model.Database;

import com.home.urix.PayRoll.Model.Employee.Employee;

import java.sql.SQLException;

public interface DataBase {
    boolean open(String dbName) throws ClassNotFoundException, SQLException;
    boolean close();
    boolean connected() throws SQLException;
    Employee[] getAllEmployees();
    boolean addEmployee(Employee employee);
    boolean removeEmployee(Employee employee);
    Integer countOfEmployees();
}
