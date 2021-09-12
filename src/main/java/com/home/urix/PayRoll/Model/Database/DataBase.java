package com.home.urix.PayRoll.Model.Database;

import com.home.urix.PayRoll.Model.Employee.Employee;

import java.sql.SQLException;

public interface DataBase {
    void open(String dbName) throws ClassNotFoundException, SQLException;
    void close() throws SQLException;
    boolean connected() throws SQLException;
    Employee[] getAllEmployees();
    boolean addEmployee(Employee employee);
    boolean removeEmployee(Employee employee);
    Integer countOfEmployees();
}
