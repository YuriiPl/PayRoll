package com.home.urix.PayRoll.Controller;

import com.home.urix.PayRoll.Model.AllocationSchema.AllocationException;
import com.home.urix.PayRoll.Model.Departments.Department;
import com.home.urix.PayRoll.Model.Departments.OrganizationStructure;
import com.home.urix.PayRoll.Model.Employee.Employee;
import com.home.urix.PayRoll.Model.Employee.EmployeeType;
import com.home.urix.PayRoll.Model.MainModel;
import com.home.urix.PayRoll.View.*;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.security.AccessControlException;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.*;


public class MainController {
    MainModel model;
    Scanner scanner;

    public MainController(){
        scanner = new Scanner(System.in);
        model = new MainModel();
    }

    private <EnumTemplate extends Enum<EnumTemplate>> EnumTemplate getIntFromEnumMenu(EnumTemplate[] enums, TextConstants msgId){
        while(true) {
            MainView.printMessageById(msgId);
            for (EnumTemplate opt : enums) {
                MainView.printMenuById(opt);
            }
            try {
                int userChoice = Integer.parseInt(scanner.nextLine());
                if(userChoice >= 0 && userChoice < enums.length){
                    return enums[userChoice];
                }
                MainView.wrongInputDataMessage();
            } catch (NumberFormatException e){
                MainView.wrongInputDataMessage();
            }
        }
    }


    private LanguageEnum getUserLanguageChoice(){
        return getIntFromEnumMenu(LanguageEnum.values(),TextConstants.CHOOSE_LANGUAGE_MESSAGE);
    }

    private MainMenuEnum getUserMenuChoice(){
        printModelInfo();
        return getIntFromEnumMenu(MainMenuEnum.values(),TextConstants.MENU_HEADER);
    }

    private EmployeeMenuEnum getEmployeeMenuChoice(){
        return getIntFromEnumMenu(EmployeeMenuEnum.values(),TextConstants.MENU_EMPLOYEES_HEADER);
    }

    private DepartmentsMenuEnum getDepartmentMenuChoice(){
        return getIntFromEnumMenu(DepartmentsMenuEnum.values(),TextConstants.MENU_DEPARTMENTS_HEADER);
    }

    private BalanceAllocationTypeEnum getBalanceAllocationType(){
        return getIntFromEnumMenu(BalanceAllocationTypeEnum.values(),TextConstants.MENU_BALANCE_ALLOCATION_TYPE);
    }

    private BalanceAllocationPlaceEnum getBalanceAllocationPlace(){
        return getIntFromEnumMenu(BalanceAllocationPlaceEnum.values(),TextConstants.MENU_BALANCE_ALLOCATION_PLACE);
    }

    private EmployeeEditMenuEnum getEmployeeEditMenuChoice(Department department, int employeeIndex) {
        MainView.printString(employeeToString(department.employees().get(employeeIndex)));
        return getIntFromEnumMenu(EmployeeEditMenuEnum.values(),TextConstants.MENU_EMPLOYEE_EDIT_CHOICE);
    }

    private EmployeeType getEmployeeType(){
        return getIntFromEnumMenu(EmployeeType.values(),TextConstants.MENU_EMPLOYEE_POSITION_TYPE);
    }

    private BigDecimal inputSalaryCentsWithScanner(Locale locale, TextConstants message,String ... vars){
        DecimalFormat df = (DecimalFormat) NumberFormat.getInstance(locale);
        df.setParseBigDecimal(true);
        while(true) {
            MainView.printString(String.format(MainView.getMessageById(message), vars));
            String userInput = scanner.nextLine();
            try {
                BigDecimal bd = ((BigDecimal) df.parseObject(userInput)).setScale(2, RoundingMode.DOWN);
                if(bd.compareTo(BigDecimal.valueOf(0))>=0) {
                    return bd.multiply(BigDecimal.valueOf(100));
                }
                MainView.wrongInputDataMessage();
            } catch (ParseException e) {
                MainView.wrongInputDataMessage();
            }
        }
    }

    private String getStringFromScanner(TextConstants messageResourceBundleId, RegExpConstants regexpResourceBundleId){
        String currentRegExp = TextFactory.getRegExpString(regexpResourceBundleId);
        MainView.printMessageById(messageResourceBundleId);
        String userInput = scanner.nextLine();
        while(!userInput.matches(currentRegExp)) {
            MainView.wrongInputDataMessage();
            MainView.printMessageById(messageResourceBundleId);
            userInput = scanner.nextLine();
        }
        return userInput;
    }

    public void startProcess(){
        try {
            model.connectToDatabase("payroll.db");
            model.loadData();
        } catch (SQLException e) {
            MainView.printMessageById(TextConstants.SQL_CONNECT_ERROR_MESSAGE);
            return;
        } catch (ClassNotFoundException e) {
            MainView.printMessageById(TextConstants.SQL_DRIVER_NOT_FOUND);
            return;
        }

//        LanguageEnum lang = getUserLanguageChoice();
        LanguageEnum lang = LanguageEnum.ENGLISH;
        MainView.changeLocale(new Locale(lang.getLanguage(),lang.getCountry()));

        MainView.printMessageById(TextConstants.GREETINGS_MESSAGE);

        MainMenuEnum userChoice;
        while((userChoice = getUserMenuChoice())!= MainMenuEnum.OPTION_EXIT) {
            switch (userChoice){
                case OPTION_SPECIFY_FUND:
                    specifyFund();
                    break;
                case OPTION_SHOW_EMPLOYEES_MENU:
                    EmployeeMenuEnum userEmployeeChoice;
                    while((userEmployeeChoice = getEmployeeMenuChoice())!= EmployeeMenuEnum.OPTION_EXIT) {
                        switch (userEmployeeChoice) {
                            case OPTION_SHOW_EMPLOYEES:
                                showAllEmployees();
                                break;
                            case OPTION_SHOW_SORT_BY_LASTNAME:
                                showAllEmployeesSortByLastName();
                                break;
                            case OPTION_SHOW_SORT_BY_START_DATE:
                                showAllEmployeesSortByStartDate();
                                break;
                            case OPTION_ADD_EMPLOYEE:
                                addNewEmployee();
                                break;
                            case OPTION_FIRE_EMPLOYEE:
                                fireAnEmployee();
                                break;
                            case OPTION_EDIT_EMPLOYEE:
                                editEmployeeData();
                                break;
                        }
                    }
                    break;
                case OPTION_DEPARTMENT_MANIPULATION:
                    DepartmentsMenuEnum userDepartmentChoice;
                    while((userDepartmentChoice = getDepartmentMenuChoice())!= DepartmentsMenuEnum.OPTION_EXIT) {
                        switch (userDepartmentChoice) {
                            case OPTION_ADD_DEPARTMENT:
                                addDepartment();
                            case OPTION_SHOW_DEPARTMENTS:
                                printDepartmentsNames();
                                break;
                            case OPTION_REMOVE_DEPARTMENT:
                                removeDepartment();
                                break;
                            case OPTION_EDIT_DEPARTMENT:
                                editDepartmentName();
                                break;
                        }
                    }
                    break;
                case OPTION_BALANCE_ALLOCATION_TYPE:
                    switch (getBalanceAllocationType()) {
                        case FLAT:
                            model.setFlatBalanceAllocation();
                            break;
                        case APPORTIONMENT:
                            model.setApportionmentBalanceAllocation();
                            break;
                        default:
                    }
                    break;
                case OPTION_BALANCE_ALLOCATION_PLACE:
                    switch (getBalanceAllocationPlace()) {
                        case BY_DEPARTMENT:
                            model.setDepartmentBalanceAllocation();
                            break;
                        case BY_ORGANIZATION:
                            model.setOrganizationBalanceAllocation();
                            break;
                        default:
                    }
                    break;
                case OPTION_PAYROLL_PRINT:
                    try {
                        model.calculate();
                    } catch (AllocationException e){
                        MainView.printMessageById(TextConstants.ERROR_SMALL_SALARY_FUND);
                        break;
                    }
                    for(OrganizationStructure structure : model.getCurrentModelDepartments()){
                        MainView.printString(structure.getName());
                        for(Employee employee: structure.employees()){
                            MainView.printString(employeeToString(employee));
                        }
                    }
                    break;
            }
        }

        MainView.printMessageById(TextConstants.GOODBYE_MESSAGE);


    }

    private void showAllEmployeesSortByStartDate() {
        LinkedList<Employee> employees = model.getAllEmployees();
        employees.sort(Comparator.comparing(Employee::getHiringDate));
        showEmployees(employees);
    }

    private void showAllEmployeesSortByLastName() {
        LinkedList<Employee> employees = model.getAllEmployees();
        employees.sort(Comparator.comparing(Employee::getLastName));
        showEmployees(employees);
    }

    private void specifyFund() {
        for(OrganizationStructure structure : model.getCurrentModelDepartments()){
            String name=structure.getName();
            if(name.length()>0) {
                structure.setFundAmount(inputSalaryCentsWithScanner(MainView.getLocale(),TextConstants.SPECIFY_SALARY_FUND_FOR, name));
            } else {
                structure.setFundAmount(inputSalaryCentsWithScanner(MainView.getLocale(),TextConstants.SPECIFY_SALARY_FUND));
            }
        }

    }

    private void addDepartment() {
        try {
            model.addDepartment(getStringFromScanner(TextConstants.ENTER_DEPARTMENT_NAME, RegExpConstants.REGEX_WORDS_NUMBERS));
        } catch (IllegalArgumentException ignored){
            MainView.printMessageById(TextConstants.ALREADY_EXISTS);
        }
    }

    private void fireAnEmployee() {
        Department department = (Department) model.departments().get(getDepartmentPosition());
        if(department.employees().size()==0){
            MainView.printMessageById(TextConstants.DEPARTMENT_IS_EMPTY);
            return;
        }
        MainView.printMessageById(TextConstants.CHOOSE_NUMBER_FOR_REMOVING_USER);
        showEmployees(department.employees());
        while(true) {
            try {
                String line = scanner.nextLine();
                if(line.length()==0)break;;
                int userChoice = Integer.parseInt(line);
                if (userChoice >= 0 && userChoice < department.employees().size()) {
                    try {
                        model.fireEmployee(userChoice, department);
                    }catch (AccessControlException e){
                        MainView.printMessageById(TextConstants.YOU_CANT_DO_THIS);
                    }
                    break;
                }
                MainView.wrongInputDataMessage();
            } catch (NumberFormatException e) {
                MainView.wrongInputDataMessage();
            }
        }
    }

    private void showAllEmployees() {
        ArrayList<OrganizationStructure> structs = model.departments();
        for (OrganizationStructure struct :structs){
            Department department = (Department)struct;
            MainView.printString(department.getName());
            if(department.employees().size()==0){
                MainView.printMessageById(TextConstants.DEPARTMENT_IS_EMPTY);
            }
            showEmployees(department.employees());
        }
    }

    private void showEmployees(LinkedList<Employee> employees) {
        int i=0;
        for(Employee user : employees){
            MainView.printString(i++ +". "+ employeeToString(user));
        }
    }

    private String employeeToString(Employee user) {
        TextConstants currentText=TextConstants.EMPLOYEE_TO_STRING;
        if(user.getPositionType()==EmployeeType.MANAGER)currentText=TextConstants.EMPLOYEE_MANAGER_TO_STRING;
        if(user.getPositionType()==EmployeeType.OTHER)currentText=TextConstants.EMPLOYEE_OTHER_TO_STRING;
        return String.format(MainView.getMessageById(currentText),
                user.getFirstName(),
                user.getLastName(),
                user.getMidName(),
                user.getBirthDay().format(DateTimeFormatter.ofPattern(TextFactory.getString(TextConstants.DATE_FORMAT))),
                user.getHiringDate().format(DateTimeFormatter.ofPattern(TextFactory.getString(TextConstants.DATE_FORMAT))),
                user.getPositionType(),
                user.getPositionName(),
                managerNameForEmployee(user),
                getEmployeeDescription(user),
                ((double) user.getSalary()) / 100,
                ((double) user.getCacheBonus()/100)
        );
//        return  "{ firstName='" + user.getFirstName() + '\'' +
//                ", midName='" + user.getMidName() + '\'' +
//                ", lastName='" + user.getLastName() + '\'' +
//                ", birthDay=" + user.getBirthDay().format(DateTimeFormatter.ofPattern(TextFactory.getString(TextConstants.DATE_FORMAT))) +
//                ", hiringDate=" + user.getHiringDate().format(DateTimeFormatter.ofPattern(TextFactory.getString(TextConstants.DATE_FORMAT))) +
//                ", salary=" + ((double) user.getSalary()) / 100 + ", bonus=" + ((double)user.getCacheBonus()/100) + " }";
    }

    private String getEmployeeDescription(Employee user) {
        return user.getDescription();
    }

    private String managerNameForEmployee(Employee e){
        try {
            Employee manager = model.getEmployeesManager(e);
            return manager.getLastName();
        } catch (RuntimeException ignore){
            return "";
        }

    }

    private void addNewEmployee() {
        if(model.departments().size()==0){
            MainView.printMessageById(TextConstants.ERROR_NO_DEPARTMENTS);
            return;
        }
        MainView.printMessageById(TextConstants.CHOOSE_DEPARTMENT);
        int departmentNumber=getDepartmentPosition();
        String lastName=getStringFromScanner(TextConstants.EMPLOYEE_ENTER_LAST_NAME, RegExpConstants.REGEXP_NAME);
        String firstName=getStringFromScanner(TextConstants.EMPLOYEE_ENTER_FIRST_NAME, RegExpConstants.REGEXP_NAME);
        String midName=getStringFromScanner(TextConstants.EMPLOYEE_ENTER_MIDDLE_NAME, RegExpConstants.REGEXP_NAME_OPT);
        LocalDate birthDay;
        LocalDate startDate;
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(TextFactory.getString(TextConstants.DATE_FORMAT));
        while(true) {
            String stringDate = getStringFromScanner(TextConstants.EMPLOYEE_ENTER_BIRTHDAY, RegExpConstants.REGEXP_DATE);
            try {
                birthDay = LocalDate.parse(stringDate, formatter);
                break;
            } catch (DateTimeParseException e) {
                MainView.wrongInputDataMessage();
            }
        }
        while(true) {
            String stringDate = getStringFromScanner(TextConstants.EMPLOYEE_ENTER_START_DATE, RegExpConstants.REGEXP_DATE);
            try {
                startDate = LocalDate.parse(stringDate, formatter);
                break;
            } catch (DateTimeParseException e) {
                MainView.wrongInputDataMessage();
            }
        }
        long salary = inputSalaryCentsWithScanner(MainView.getLocale(),TextConstants.EMPLOYEE_SALARY).longValue();
        EmployeeType employeeType = getEmployeeType();
        String positionName=getStringFromScanner(TextConstants.EMPLOYEE_ENTER_POSITION_NAME, RegExpConstants.REGEX_WORDS_NUMBERS);
        int posInDep=model.addNewEmployee(departmentNumber,firstName,midName,lastName,birthDay,startDate,salary,employeeType,positionName);
        if(employeeType == EmployeeType.OTHER){
            model.editEmployeeDescription(posInDep,departmentNumber,getStringFromScanner(TextConstants.EMPLOYEE_SPECIFY_DESCRIPTION, RegExpConstants.REGEX_WORDS_NUMBERS));
        }
    }

    int getIndexEmployeeFromUser(OrganizationStructure struct, TextConstants textId){
        MainView.printMessageById(textId);
        showEmployees(struct.employees());
        int employeeIndex;
        while(true) {
            try {
                employeeIndex = Integer.parseInt(scanner.nextLine());
                if (employeeIndex >= 0 && employeeIndex < struct.employees().size()) {
                    break;
                }
                MainView.wrongInputDataMessage();
            } catch (NumberFormatException e) {
                MainView.wrongInputDataMessage();
            }
        }
        return employeeIndex;
    }

    private void editEmployeeData() {
        MainView.printMessageById(TextConstants.CHOOSE_DEPARTMENT);
        Department department = (Department) model.departments().get(getDepartmentPosition());
        if(department.employees().size()==0){
            MainView.printMessageById(TextConstants.DEPARTMENT_IS_EMPTY);
            return;
        }
        int employeeIndex = getIndexEmployeeFromUser(department,TextConstants.CHOOSE_NUMBER_FOR_EDITING_USER);
        EmployeeEditMenuEnum editMenuEnum;
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(TextFactory.getString(TextConstants.DATE_FORMAT));
        while((editMenuEnum = getEmployeeEditMenuChoice(department,employeeIndex))!= EmployeeEditMenuEnum.OPTION_EXIT) {
            switch (editMenuEnum){
                case OPTION_FIRSTNAME:
                    String firstName=getStringFromScanner(TextConstants.EMPLOYEE_ENTER_FIRST_NAME, RegExpConstants.REGEXP_NAME);
                    model.editEmployeesFirstName(employeeIndex,department,firstName);
                    break;
                case OPTION_MIDDLE_NAME:
                    String midName=getStringFromScanner(TextConstants.EMPLOYEE_ENTER_MIDDLE_NAME, RegExpConstants.REGEXP_NAME_OPT);
                    model.editEmployeesMiddleName(employeeIndex,department,midName);
                    break;
                case OPTION_LASTNAME:
                    String lastName=getStringFromScanner(TextConstants.EMPLOYEE_ENTER_LAST_NAME, RegExpConstants.REGEXP_NAME);
                    model.editEmployeesLastName(employeeIndex,department,lastName);
                    break;
                case OPTION_BIRTHDAY:
                    while(true) {
                        String stringDate = getStringFromScanner(TextConstants.EMPLOYEE_ENTER_BIRTHDAY, RegExpConstants.REGEXP_DATE);
                        try {
                            model.editEmployeesBirthday(employeeIndex,department,LocalDate.parse(stringDate, formatter));
                            break;
                        } catch (DateTimeParseException e) {
                            MainView.wrongInputDataMessage();
                        }
                    }
                    break;
                case OPTION_HIRE_DATE:
                    while(true) {
                        String stringDate = getStringFromScanner(TextConstants.EMPLOYEE_ENTER_START_DATE, RegExpConstants.REGEXP_DATE);
                        try {
                            model.editEmployeesStartWorkingDate(employeeIndex,department,LocalDate.parse(stringDate, formatter));
                            break;
                        } catch (DateTimeParseException e) {
                            MainView.wrongInputDataMessage();
                        }
                    }
                    break;
                case OPTION_SALARY:
                    long salary = inputSalaryCentsWithScanner(MainView.getLocale(),TextConstants.EMPLOYEE_SALARY).longValue();
                    model.editEmployeesSalary(employeeIndex,department,salary);
                    break;
                case OPTION_SET_DESCRIPTION:

                    model.editEmployeeDescription(employeeIndex,department,getStringFromScanner(TextConstants.EMPLOYEE_SPECIFY_DESCRIPTION, RegExpConstants.REGEX_WORDS_NUMBERS));
                    break;
                case OPTION_DEPARTMENT:
                    MainView.printMessageById(TextConstants.CHOOSE_DEPARTMENT);
                    int departmentNumber=getDepartmentPosition();
                    model.editEmployeesDepartment(employeeIndex, department, departmentNumber);
                    return;
                case OPTION_POSITION_TYPE:
                    EmployeeType employeeType = getEmployeeType();
                    model.editEmployeesPositionType(employeeIndex,department,employeeType);
                    break;
                case OPTION_POSITION_NAME:
                    String positionName=getStringFromScanner(TextConstants.EMPLOYEE_ENTER_POSITION_NAME, RegExpConstants.REGEX_WORDS_NUMBERS);
                    model.editEmployeesPositionName(employeeIndex,department,positionName);
                    break;
                case OPTION_SET_MANAGER:
                    int managerIndex = getIndexEmployeeFromUser(department,TextConstants.CHOOSE_MANAGER_NUMBER);
                    if(employeeIndex==managerIndex) {
                        MainView.printMessageById(TextConstants.YOU_CANT_DO_THIS);
                        break;
                    }
                    try {
                        model.editEmployeesManager(employeeIndex, department, managerIndex);
                    } catch (AccessControlException e){
                        MainView.printMessageById(TextConstants.YOU_CANT_DO_THIS);
                    }
                    break;
            }
        }
    }

    private int getDepartmentPosition() {
        ArrayList<OrganizationStructure> departments = model.departments();
        int departmentNumber;
        printDepartmentsNames();
        while(true) {
            try {
                String userLine=scanner.nextLine();
                departmentNumber = Integer.parseInt(userLine);
                if(departmentNumber >= 0 && departmentNumber < departments.size()){
                    break;
                }
                MainView.wrongInputDataMessage();
            } catch (NumberFormatException e){
                MainView.wrongInputDataMessage();
            }
            printDepartmentsNames();
        }
        return departmentNumber;
    }

    private void editDepartmentName() {
        ArrayList<OrganizationStructure> departments = model.departments();
        printDepartmentsNames();
        if(departments.size()==0)return;
        while(true) {
            try {
                String userLine=scanner.nextLine();
                if(userLine.length()==0)return;
                int userChoice = Integer.parseInt(userLine);
                if(userChoice >= 0 && userChoice < departments.size()){
                    userLine=getStringFromScanner(TextConstants.ENTER_DEPARTMENT_NAME, RegExpConstants.REGEX_WORDS_NUMBERS);
                    model.changeDepartmentName(userChoice,userLine);
                    printDepartmentsNames();
                    return;
                }
                MainView.wrongInputDataMessage();
            } catch (NumberFormatException e){
                MainView.wrongInputDataMessage();
            }
            printDepartmentsNames();
        }
    }

    private void printDepartmentsNames() {
        ArrayList<OrganizationStructure> departments = model.departments();
        int i=0;
        StringBuilder listDepts = new StringBuilder(TextFactory.getString(TextConstants.TEXT_DEPARTMENT_LIST));
        for(OrganizationStructure dep : departments){
            listDepts.append("\n").append(i++).append(". ").append(dep.getName());
        }
        MainView.printString(listDepts.toString());
    }

    private void removeDepartment(){
        ArrayList<OrganizationStructure> departments = model.departments();
        printDepartmentsNames();
        if(departments.size()==0)return;
        while(true) {
            try {
                String userLine=scanner.nextLine();
                if(userLine.length()==0)return;
                int userChoice = Integer.parseInt(userLine);
                if(userChoice >= 0 && userChoice < departments.size()){
                    try {
                        model.removeDepartment(userChoice);
                    } catch (RuntimeException ignored){
                        MainView.printMessageById(TextConstants.DEPARTMENT_NOT_EMPTY);
                    }
                    printDepartmentsNames();
                    return;
                }
                MainView.wrongInputDataMessage();
            } catch (NumberFormatException e){
                MainView.wrongInputDataMessage();
            }
            printDepartmentsNames();
        }
    }

    private void printModelInfo() {
//        MainView.printString(
//                "[" +
//                        " Departments:"+model.departments().size()+
//                        ", Count of employees:"+model.countOfEmployees()+
//                        ", Balance allocation: "+model.fundAllocationSchemaType()+
//                        " "+model.fundDestinationSchemaType()+
//                " ]"
//        );
        MainView.printString(String.format(MainView.getMessageById(TextConstants.FOR_MODEL_INFO),
                model.departments().size(),
                model.countOfEmployees(),
                model.fundAllocationSchemaType(),
                model.fundDestinationSchemaType()
                ));
    }


}
