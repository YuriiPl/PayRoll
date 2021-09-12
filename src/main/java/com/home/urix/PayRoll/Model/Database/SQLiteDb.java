package com.home.urix.PayRoll.Model.Database;

import com.home.urix.PayRoll.Model.Employee.Employee;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class SQLiteDb implements DataBase {
    Connection connection = null;
    @Override
    public boolean open(String dbName) throws ClassNotFoundException, SQLException {
//        try {
            String driverName = "org.sqlite.JDBC";
            Class.forName(driverName);
//        } catch (ClassNotFoundException e) {
//            //System.out.println("Can't get class. No driver found");
//            return false;
//        }
        try {
            String connectionString = "jdbc:sqlite:";
            connection = DriverManager.getConnection(connectionString + dbName);
        } catch (SQLException e) {
            //System.out.println("Can't get connection. Incorrect URL");
            return false;
        }
        return true;
    }

    @Override
    public boolean close() {
        try {
            connection.close();
        } catch (SQLException e) {
            //System.out.println("Can't close connection");
            return false;
        }
        return true;
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
