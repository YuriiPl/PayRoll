package com.home.urix.PayRoll.Model.Employee;

import java.time.LocalDate;


public class Employee {
    private long id;
    private String firstName, midName, lastName;
    private LocalDate birthDay;
    private LocalDate hiringDate;
    private long salary;
    private long cacheBonus;

    public Employee(String firstName, String midName, String lastName, LocalDate birthDay, LocalDate hiringDate, long salary) {
        this.firstName = firstName;
        this.midName = midName;
        this.lastName = lastName;
        this.birthDay = birthDay;
        this.hiringDate = hiringDate;
        this.salary = salary;
        cacheBonus=0;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getFullName() {
        return firstName+" "+midName+" "+lastName;
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

    public long getBonus() {
        return cacheBonus;
    }

    public void setBonus(long bonus) {
        this.cacheBonus = bonus;
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

    @Override
    public String toString() {
        return  "{ firstName='" + firstName + '\'' +
                ", midName='" + midName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", birthDay=" + birthDay +
                ", hiringDate=" + hiringDate +
                ", salary=" + ((float)salary)/100 +
                ", bonus=" + cacheBonus +
                " }";
    }
}
