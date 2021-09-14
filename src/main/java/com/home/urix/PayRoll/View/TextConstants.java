package com.home.urix.PayRoll.View;

public enum TextConstants {
    WRONG_INPUT_DATA("input.string.data.wrong"),
    GREETINGS_MESSAGE("start.greeting.message"),
    CHOOSE_LANGUAGE_MESSAGE("choose.your.language"),
    GOODBYE_MESSAGE("goodbye.message"),
    SQL_CONNECT_ERROR_MESSAGE("sql.connect.error"),
    SQL_DRIVER_NOT_FOUND("sql.driver.not.found"),
    MENU_HEADER("menu.header"),
    MENU_EMPLOYEES_HEADER("menu.employees.header"),
    MENU_DEPARTMENTS_HEADER("menu.departments.header"),
    MENU_BALANCE_ALLOCATION_TYPE("balance.allocation.type"),
    MENU_BALANCE_ALLOCATION_PLACE("balance.allocation.place"),
    MENU_EMPLOYEE_EDIT_CHOICE("menu.employee.edit"),
    MENU_EMPLOYEE_POSITION_TYPE("menu.employee.position.type"),
    ENTER_DEPARTMENT_NAME("enter.department.name"),
    ALREADY_EXISTS("error.already.exists"),
    DEPARTMENT_NOT_EMPTY("error.department.not.empty"),
    DEPARTMENT_IS_EMPTY("department.is.empty"),
    TEXT_DEPARTMENT_LIST("text.department.list"),
    EMPLOYEE_ENTER_FIRST_NAME("employee.enter.first.name"),
    EMPLOYEE_ENTER_LAST_NAME("employee.enter.last.name"),
    EMPLOYEE_ENTER_MIDDLE_NAME("employee.enter.middle.name"),
    EMPLOYEE_ENTER_BIRTHDAY("employee.enter.birthday"),
    EMPLOYEE_ENTER_START_DATE("employee.enter.start.date"),
    EMPLOYEE_ENTER_POSITION_NAME("employee.enter.position.name"),
    EMPLOYEE_SALARY("employee.specify.salary"),
    DATE_FORMAT("date.format"),
    CHOOSE_DEPARTMENT("choose.department"),
    CHOOSE_NUMBER_FOR_REMOVING_USER("choose.number.to.remove.user"),
    CHOOSE_NUMBER_FOR_EDITING_USER("choose.number.to.edit.user"),
    ERROR_NO_DEPARTMENTS("error.no.departments"),
    SPECIFY_SALARY_FUND("specify.salary.fund"),
    ERROR_SMALL_SALARY_FUND("small.salary.fund"),
    SPECIFY_SALARY_FUND_FOR("specify.salary.fund.for"),
    FOR_MODEL_INFO("format.model.info"),
    EMPLOYEE_TO_STRING("format.employee.to.string"),
    YOU_CANT_DO_THIS("error.no.permissions");

    private final String value;
    TextConstants(String value){
        this.value = value;
    }
    public String value(){
        return value;
    }

}
