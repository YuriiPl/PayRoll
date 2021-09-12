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
                    for(OrganizationStructure structure : model.getCalculationSchema().getOrganizationStructures()){
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

    private void printModelInfo() {
        MainView.printString(
                "[" +
                //0model.getSalaryFundCents() +
                "]"
        );
    }


}
