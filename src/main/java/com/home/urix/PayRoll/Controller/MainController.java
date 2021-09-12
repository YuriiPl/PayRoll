package com.home.urix.PayRoll.Controller;

import com.home.urix.PayRoll.Model.Departments.OrganizationStructure;
import com.home.urix.PayRoll.Model.Employee.Employee;
import com.home.urix.PayRoll.Model.MainModel;
import com.home.urix.PayRoll.View.*;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Locale;
import java.util.Scanner;

public class MainController {
    MainView view;
    MainModel model;
    Scanner scanner;

    public MainController(){
        scanner = new Scanner(System.in);
        model = new MainModel();
    }

    private interface VoidFunction {
        public void showMenu();
    }

    private <EnumTemplate extends Enum<EnumTemplate>> EnumTemplate showEnumMenu(EnumTemplate[] enums, VoidFunction menu){
        while(true) {
            menu.showMenu();
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
        return showEnumMenu(LanguageEnum.values(),MainView::showLanguageMenu);
    }

    private MainMenuEnum getUserMenuChoice(){
        return showEnumMenu(MainMenuEnum.values(),MainView::showActionMenu);
    }

    private EmployeeMenuEnum getEmployeeMenuChoice(){
        return showEnumMenu(EmployeeMenuEnum.values(),MainView::showEmployeesMenu);
    }

    private DepartmentsMenuEnum getDepartmentMenuChoice(){
        return showEnumMenu(DepartmentsMenuEnum.values(),MainView::showDepartmentMenu);
    }

    private BalanceAllocationTypeEnum getBalanceAllocationType(){
        return showEnumMenu(BalanceAllocationTypeEnum.values(),MainView::showBalanceAllocationTypeMenu);
    }

    private BalanceAllocationPlaceEnum getBalanceAllocationPlace(){
        return showEnumMenu(BalanceAllocationPlaceEnum.values(),MainView::showBalanceAllocationPlaceMenu);
    }

    private BigDecimal inputSalaryFundWithScanner(Locale locale){
        DecimalFormat df = (DecimalFormat) NumberFormat.getInstance(locale);
        df.setParseBigDecimal(true);
        while(true) {
            MainView.printMessageById(TextConstants.SPECIFY_SALARY_FUND);
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
            MainView.inputWrongDataMessage();
            MainView.printMessageById(messageResourceBundleId);
            userInput = scanner.nextLine();
        }
        return userInput;
    }

    public void startProcess(){
        try {
            model.connectToDatabase("payroll.db");
        } catch (SQLException e) {
            MainView.printMessageById(TextConstants.SQL_CONNECT_ERROR_MESSAGE);
            return;
        } catch (ClassNotFoundException e) {
            MainView.printMessageById(TextConstants.SQL_DRIVER_NOT_FOUND);
            return;
        }

        LanguageEnum lang = getUserLanguageChoice();
        Locale locale = new Locale(lang.getLanguage(),lang.getCountry());
        MainView.changeLocale(locale);

        MainView.printMessageById(TextConstants.GREETINGS_MESSAGE);

        MainMenuEnum userChoice;
        while((userChoice = getUserMenuChoice())!= MainMenuEnum.OPTION_EXIT) {
            switch (userChoice){
                case OPTION_SPECIFY_FUND:
                    //model.setSalaryFundCents(inputSalaryFundWithScanner(MainView.getLocale()));
                    break;
                case OPTION_SHOW_EMPLOYEES_MENU:
                    EmployeeMenuEnum userEmployeeChoice;
                    while((userEmployeeChoice = getEmployeeMenuChoice())!= EmployeeMenuEnum.OPTION_EXIT) {
                        switch (userEmployeeChoice) {
                            case OPTION_SHOW_EMPLOYEES:
                                break;
                            case OPTION_ADD_EMPLOYEE:
                                break;
                            case OPTION_FIRE_EMPLOYEE:
                                break;
                        }
                    }
                    break;
                case OPTION_DEPARTMENT_MANIPULATION:
                    DepartmentsMenuEnum userDepartmentChoice;
                    while((userDepartmentChoice = getDepartmentMenuChoice())!= DepartmentsMenuEnum.OPTION_EXIT) {
                        switch (userDepartmentChoice) {
                            case OPTION_ADD_DEPARTMENT:
                                try {
                                    model.addDepartment(getStringFromScanner(TextConstants.ENTER_DEPARTMENT_NAME, RegExpConstants.REGEX_WORDS_NUMBERS));
                                } catch (IllegalArgumentException ignored){
                                    MainView.printMessageById(TextConstants.ALREADY_EXISTS);
                                }
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
                    model.calculate();
                    for(OrganizationStructure structure : model.getCurrentModelDepartments()){
                        MainView.printString(structure.getName());
                        for(Employee employee: structure.employees()){
                            MainView.printString(String.valueOf(employee));
                        }
                    }
                    break;
            }
            printModelInfo();
        }

        MainView.printMessageById(TextConstants.GOODBYE_MESSAGE);


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
            if(i%5==0)listDepts.append("\n");
            listDepts.append(i++).append(". ").append(dep.getName()).append(" ");
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
        MainView.printString(
                "[" +
                //0model.getSalaryFundCents() +
                "]"
        );
    }


}
