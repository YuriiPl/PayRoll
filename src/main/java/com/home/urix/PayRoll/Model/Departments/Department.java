package com.home.urix.PayRoll.Model.Departments;

import com.home.urix.PayRoll.Model.Employee.Employee;
import com.home.urix.PayRoll.Model.Employee.EmployeeType;
import com.home.urix.PayRoll.View.TextConstants;
import com.home.urix.PayRoll.View.TextFactory;

import java.security.AccessControlException;
import java.util.LinkedList;

public class Department extends OrganizationStructure {
    private final LinkedList<Employee> employeesList = new LinkedList<>();
    private long id;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Department(String name, long id){
        setName(name);
        setId(id);
    }

    public Employee fireEmployee(int pos){
        Employee employee = employeesList.get(pos);
        if(employee.getPositionType()==EmployeeType.MANAGER) {
            LinkedList<Employee> managers = findAllOtherManagers(employee);
            if(managers.size()==0) throw new AccessControlException(TextFactory.getString(TextConstants.YOU_CANT_DO_THIS));
            Employee otherManager=managers.get(0);
            for(Employee employee1 : employeesList){
                if(employee1.getManagerId()==employee.getId()){
                    employee1.setManager(otherManager);
                }
            }
        }
        return employeesList.remove(pos);
    }

    @Override
    public LinkedList<Employee> employees() {
        return employeesList;
    }

    public void addNewEmployee(Employee employee){
        employeesList.add(employee);
    }

    public LinkedList<Employee> findAllOtherManagers(Employee manager){
        LinkedList<Employee> managers = new LinkedList<>();
        for (Employee employee : employeesList){
            if(employee==manager)continue;
            if(employee.getPositionType() == EmployeeType.MANAGER)managers.add(employee);
        }
        return managers;
    }
}
