package com.home.urix.PayRoll.Model.Database;

import com.home.urix.PayRoll.Model.Departments.Department;
import com.home.urix.PayRoll.Model.Departments.OrganizationStructure;
import com.home.urix.PayRoll.Model.Employee.Employee;
import com.home.urix.PayRoll.Model.Employee.EmployeeType;

import java.sql.SQLException;
import java.time.LocalDate;

public interface DataBase {
    void open(String dbName) throws ClassNotFoundException, SQLException;
    void close() throws SQLException;
    boolean connected() throws SQLException;
    Employee[] getAllEmployees();

    boolean addEmployee(Employee employee, long departmentId);

    Integer employeesCount();
    Department[] getAllDepartments();
    int departmentsCount();
    Department createNewDepartment(String departmentName);

    void removeDepartment(Department department);

    void fireEmployee(Employee employee);

    boolean editEmployeesFirstName(long employeesId, String firstName);

    boolean editEmployeesMiddleName(long id, String midName);

    boolean editEmployeesLastName(long id, String lastName);

    boolean editEmployeesBirthday(long id, LocalDate birthDay);

    boolean editEmployeesStartWorkingDate(long id, LocalDate startWorkingDate);

    boolean editEmployeesSalary(long id, long salary);

    boolean editEmployeesDepartment(long id, long newDepartmentId);

    boolean changeDepartmentName(long id, String newName);

    boolean editEmployeesPositionName(long id, String positionName);

    boolean editEmployeesPositionType(long id, EmployeeType employeeType);

    boolean editEmployeesManager(long id, long id1);

    boolean editEmployeeDescription(long id, String description);
}

