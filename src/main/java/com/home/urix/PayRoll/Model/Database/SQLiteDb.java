package com.home.urix.PayRoll.Model.Database;

import com.home.urix.PayRoll.Model.Employee.Employee;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class SQLiteDb implements DataBase {
    Connection connection = null;
    @Override
    public void open(String dbName) throws ClassNotFoundException, SQLException {
            String driverName = "org.sqlite.JDBC";
            Class.forName(driverName);
            String connectionString = "jdbc:sqlite:";
            connection = DriverManager.getConnection(connectionString + dbName);
    }

    @Override
    public void close() throws SQLException {
            connection.close();
    }

    @Override
    public boolean connected() throws SQLException {
        return connection != null && !connection.isClosed();
    }

    @Override
    public Employee[] getAllEmployees() {
        return new Employee[0];
    }

    @Override
    public boolean addEmployee(Employee employee) {
        return false;
    }

    @Override
    public boolean removeEmployee(Employee employee) {
        return false;
    }

    @Override
    public Integer countOfEmployees() {
        return null;
    }
}
