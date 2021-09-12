package com.home.urix.PayRoll.View;

public enum TextConstants {
    WRONG_INPUT_DATA("input.string.data.wrong"),
    GREETINGS_MESSAGE("start.greeting.message"),
    CHOOSE_LANGUAGE_MESSAGE("choose.your.language"),
    SPECIFY_SALARY_FUND("specify.salary.fund"),
    GOODBYE_MESSAGE("goodbye.message"),
    SQL_CONNECT_ERROR_MESSAGE("sql.connect.error"),
    SQL_DRIVER_NOT_FOUND("sql.driver.not.found"),
    MENU_HEADER("menu.header"),
    MENU_EMPLOYEES_HEADER("menu.employees.header"),
    MENU_DEPARTMENTS_HEADER("menu.departments.header"),
    MENU_BALANCE_ALLOCATION_TYPE("balance.allocation.type"),
    MENU_BALANCE_ALLOCATION_PLACE("balance.allocation.place"),
    ENTER_DEPARTMENT_NAME("enter.department.name"),
    ALREADY_EXISTS("error.already.exists"),
    DEPARTMENT_NOT_EMPTY("error.department.not.empty"),
    TEXT_DEPARTMENT_LIST("text.department.list");

    private final String value;

    TextConstants(String value){
        this.value = value;
    }

    public String value(){
        return value;
    }

}
