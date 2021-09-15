package com.home.urix.PayRoll.Model.Employee;

import com.home.urix.PayRoll.View.TextConstants;
import com.home.urix.PayRoll.View.TextFactory;

import java.security.AccessControlException;
import java.time.LocalDate;
import java.util.LinkedList;


public class Employee {
    private long id;
    private long departmentId;
    private String firstName, midName, lastName;
    private LocalDate birthDay;
    private LocalDate hiringDate;
    private long salary;
    private long cacheBonus;

    private EmployeeType positionType;
    private String positionName;

    private long managerId=0;
    private String description="";

    public Employee(String firstName, String midName, String lastName,
                    LocalDate birthDay, LocalDate hiringDate,
                    long salary,
                    EmployeeType positionType,
                    String positionMane) {
        this.firstName = firstName;
        this.midName = midName;
        this.lastName = lastName;
        this.birthDay = birthDay;
        this.hiringDate = hiringDate;
        this.salary = salary;
        this.cacheBonus=0;
        this.id=0;
        this.departmentId=0;
        this.positionType=positionType;
        this.positionName=positionMane;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getDepartmentId() {
        return departmentId;
    }

    public void setDepartmentId(long departmentId) {
        this.departmentId = departmentId;
    }

    public long getCacheBonus() {
        return cacheBonus;
    }

    public void setCacheBonus(long cacheBonus) {
        this.cacheBonus = cacheBonus;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getMidName() {
        return midName;
    }

    public void setMidName(String midName) {
        this.midName = midName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public long getSalary() {
        return salary;
    }

    public void setSalary(long salary) {
        this.salary = salary;
    }

    public LocalDate getBirthDay() {
        return birthDay;
    }

    public void setBirthDay(LocalDate birthDay) {
        this.birthDay = birthDay;
    }

    public LocalDate getHiringDate() {
        return hiringDate;
    }

    public void setHiringDate(LocalDate hiringDate) {
        this.hiringDate = hiringDate;
    }

    public String getPositionName() {
        return positionName;
    }

    public void setPositionName(String positionName) {
        this.positionName = positionName;
    }

    public EmployeeType getPositionType() {
        return positionType;
    }

    public void setPositionType(EmployeeType positionType) {
        this.positionType = positionType;
    }

//    void addSubordinate(Employee e){
//        throw new AccessControlException(TextFactory.getString(TextConstants.YOU_CANT_DO_THIS));
//    }
//
//    void addSubordinates(LinkedList<Employee> e){
//        throw new AccessControlException(TextFactory.getString(TextConstants.YOU_CANT_DO_THIS));
//    }

    public void setDescription(String description){
        if(positionType==EmployeeType.OTHER){
            this.description=description;
        }else {
            throw new AccessControlException(TextFactory.getString(TextConstants.YOU_CANT_DO_THIS));
        }
    }

    public void setManager(Employee e){
        if(e.positionType==EmployeeType.MANAGER){
            managerId=e.getId();
        } else {
            throw new AccessControlException(TextFactory.getString(TextConstants.YOU_CANT_DO_THIS));
        }
    }

    public void setManagerIdFromDb(long managerId){
        this.managerId=managerId;
    }

    public String getDescription(){
        return description;
    }

    public long getManagerId(){
        return id;
    }

}
