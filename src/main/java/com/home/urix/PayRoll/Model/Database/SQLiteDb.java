package com.home.urix.PayRoll.Model.Database;

import com.home.urix.PayRoll.Model.Departments.Department;
import com.home.urix.PayRoll.Model.Employee.Employee;

import java.sql.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class SQLiteDb implements DataBase {
    Connection connection = null;
    private static final String GET_ALL_DEPARTMENTS="SELECT id, name FROM Department";
    private static final String GET_ALL_DEPARTMENTS_COUNT="SELECT COUNT(id) FROM Department";
    private static final String GET_ALL_EMPLOYEES_COUNT = "SELECT COUNT(id) FROM Employee";
    private static final String GET_ALL_EMPLOYEES="SELECT * FROM Employee";
    private static final String SAVE_DEPARTMENT = "INSERT INTO Department (Name) Values (?)";
    private static final String REMOVE_DEPARTMENT = "DELETE FROM Department WHERE id=?";
    private static final String EDIT_DEPARTMENT_NAME = "UPDATE Department SET Name=? WHERE id=?";
    private static final String FIRE_EMPLOYEE = "DELETE FROM Employee WHERE id=?";
    private static final String INSERT_INTO_EMPLOYEE = "INSERT INTO Employee (FirstName, LastName, MidName, BirthDay, HiringDay, Payment, DepartmentId) VALUES ( ?, ?, ?, ?, ?, ?, ?);";
    private static final String REMOVE_EMPLOYEE="DELETE FROM Employee WHERE id=?";
    private static final String EDIT_EMPLOYEES_FIRSTNAME="UPDATE Employee SET FirstName=? WHERE id=?";
    private static final String EDIT_EMPLOYEES_MIDDLE_NAME="UPDATE Employee SET MidName=? WHERE id=?";
    private static final String EDIT_EMPLOYEES_LASTNAME="UPDATE Employee SET LastName=? WHERE id=?";
    private static final String EDIT_EMPLOYEES_BIRTH_DAY ="UPDATE Employee SET BirthDay=? WHERE id=?";
    private static final String EDIT_EMPLOYEES_HIRING_DAY ="UPDATE Employee SET HiringDay=? WHERE id=?";
    private static final String EDIT_EMPLOYEES_PAYMENT ="UPDATE Employee SET Payment=? WHERE id=?";
    private static final String EDIT_EMPLOYEES_DEPARTMENT ="UPDATE Employee SET DepartmentId=? WHERE id=?";

    @Override
    public void open(String dbName) throws ClassNotFoundException, SQLException {
            String driverName = "org.sqlite.JDBC";
            Class.forName(driverName);
            String connectionString = "jdbc:sqlite:";
            connection = DriverManager.getConnection(connectionString + dbName);
    }

    private int getCount(String request){
        int count=0;
        try(Statement stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery(request)
        ) {
            rs.next();
            count=rs.getInt(1);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return count;
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
        Employee[] employees = new Employee[employeesCount()];
        try(Statement stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery(GET_ALL_EMPLOYEES)
        ) {
            int i=0;
            while(rs.next()){
                Employee d = new Employee(rs.getString("FirstName"),rs.getString("MidName"),rs.getString("LastName"),
                        LocalDate.parse(rs.getString("BirthDay"), DateTimeFormatter.ofPattern("MM-dd-yyyy")),
                        LocalDate.parse(rs.getString("HiringDay"), DateTimeFormatter.ofPattern("MM-dd-yyyy")),
                        rs.getLong("Payment"));
                d.setId(rs.getInt("id"));
                d.setDepartmentId(rs.getLong("DepartmentId"));
                employees[i++]=d;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return employees;
    }

    @Override
    public Integer employeesCount() {
        return getCount(GET_ALL_EMPLOYEES_COUNT);
    }

    @Override
    public Department[] getAllDepartments() {
        Department[] departments = new Department[departmentsCount()];
        try(Statement stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery(GET_ALL_DEPARTMENTS)
        ) {
            int i=0;
            while(rs.next()){
                Department d = new Department(rs.getString("name"),rs.getInt("id"));
                departments[i++]=d;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return departments;
    }

    @Override
    public int departmentsCount() {
        return getCount(GET_ALL_DEPARTMENTS_COUNT);
    }

    @Override
    public Department createNewDepartment(String departmentName) {
        long departmentId=0;
        try(
            PreparedStatement statement = connection.prepareStatement(SAVE_DEPARTMENT,Statement.RETURN_GENERATED_KEYS)
        ) {
            statement.setString(1, departmentName);
            int affectedRows = statement.executeUpdate();
            if (affectedRows == 0) {
                throw new SQLException("Creating user failed, no rows affected.");
            }
            try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    departmentId=generatedKeys.getLong(1);
                }
                else {
                    throw new SQLException("Creating user failed, no ID obtained.");
                }
            }
        } catch (SQLException e) {
            //System.out.println(e.getErrorCode());
            e.printStackTrace();
        }
        return new Department(departmentName, departmentId);
    }

    @Override
    public void removeDepartment(Department department) {
        removeFromDbByID(REMOVE_DEPARTMENT, department.getId());
    }

    @Override
    public boolean changeDepartmentName(long id, String newName) {
        return updateDbStringDataById(EDIT_DEPARTMENT_NAME, id, newName);
    }

    private void removeFromDbByID(String sqlDeleteString, long id) {
        try(
                PreparedStatement statement = connection.prepareStatement(sqlDeleteString,Statement.RETURN_GENERATED_KEYS)
        ) {
            statement.setLong(1, id);
            int affectedRows = statement.executeUpdate();
            if (affectedRows == 0) {
                throw new SQLException("Deleting failed, no rows affected.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void fireEmployee(Employee employee) {
        removeFromDbByID(FIRE_EMPLOYEE, employee.getId());
    }


    private boolean updateDbStringDataById(String sqlDeleteString, long id, String data) {
        try(
                PreparedStatement statement = connection.prepareStatement(sqlDeleteString,Statement.RETURN_GENERATED_KEYS)
        ) {
            statement.setString(1, data);
            statement.setLong(2, id);
            int affectedRows = statement.executeUpdate();
            if (affectedRows == 0) {
                throw new SQLException("Update failed, no rows affected.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    private boolean updateDbLongDataById(String sqlDeleteString, long id, long data) {
        try(
                PreparedStatement statement = connection.prepareStatement(sqlDeleteString,Statement.RETURN_GENERATED_KEYS)
        ) {
            statement.setLong(1, data);
            statement.setLong(2, id);
            int affectedRows = statement.executeUpdate();
            if (affectedRows == 0) {
                throw new SQLException("Deleting failed, no rows affected.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }


    @Override
    public boolean editEmployeesFirstName(long employeesId, String firstName) {
        return updateDbStringDataById(EDIT_EMPLOYEES_FIRSTNAME,employeesId,firstName);
    }

    @Override
    public boolean editEmployeesMiddleName(long id, String midName) {
        return updateDbStringDataById(EDIT_EMPLOYEES_MIDDLE_NAME,id,midName);
    }

    @Override
    public boolean editEmployeesLastName(long id, String lastName) {
        return updateDbStringDataById(EDIT_EMPLOYEES_LASTNAME,id,lastName);
    }

    @Override
    public boolean editEmployeesBirthday(long id, LocalDate birthDay) {
        return updateDbStringDataById(EDIT_EMPLOYEES_BIRTH_DAY,id,birthDay.format(DateTimeFormatter.ofPattern("MM-dd-yyyy")));
    }

    @Override
    public boolean editEmployeesStartWorkingDate(long id, LocalDate startWorkingDate) {
        return updateDbStringDataById(EDIT_EMPLOYEES_HIRING_DAY,id,startWorkingDate.format(DateTimeFormatter.ofPattern("MM-dd-yyyy")));
    }

    @Override
    public boolean editEmployeesSalary(long id, long salary) {
        return updateDbLongDataById(EDIT_EMPLOYEES_PAYMENT,id,salary);
    }

    @Override
    public boolean editEmployeesDepartment(long id, long newDepartmentId) {
        return updateDbLongDataById(EDIT_EMPLOYEES_DEPARTMENT,id,newDepartmentId);
    }


    @Override
    public boolean addEmployee(Employee employee, long departmentId) {
        long userId=0;
        try(
                PreparedStatement statement = connection.prepareStatement(INSERT_INTO_EMPLOYEE,Statement.RETURN_GENERATED_KEYS)
        ) {
            statement.setString(1, employee.getFirstName());
            statement.setString(2, employee.getLastName());
            statement.setString(3, employee.getMidName());
            statement.setString(4, employee.getBirthDay().format(DateTimeFormatter.ofPattern("MM-dd-yyyy")));
            statement.setString(5, employee.getHiringDate().format(DateTimeFormatter.ofPattern("MM-dd-yyyy")));
            statement.setLong(6, employee.getSalary());
            statement.setLong(7, departmentId);
            int affectedRows = statement.executeUpdate();
            if (affectedRows == 0) {
                throw new SQLException("Creating user failed, no rows affected.");
            }
            try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    userId=generatedKeys.getLong(1);
                }
                else {
                    throw new SQLException("Creating user failed, no ID obtained.");
                }
            }
        } catch (SQLException e) {
            //System.out.println(e.getErrorCode());
            e.printStackTrace();
        }
        employee.setId(userId);
        return true;
    }

    @Override
    public boolean removeEmployee(Employee employee) {
        try(
                PreparedStatement statement = connection.prepareStatement(REMOVE_EMPLOYEE,Statement.RETURN_GENERATED_KEYS)
        ) {
            statement.setLong(1, employee.getId());
            int affectedRows = statement.executeUpdate();
            if (affectedRows == 0) {
                throw new SQLException("Deleting failed, no rows affected.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return true;
    }

}
