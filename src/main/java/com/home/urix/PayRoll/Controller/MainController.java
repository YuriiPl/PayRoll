package com.home.urix.PayRoll.Controller;

import com.home.urix.PayRoll.Model.MainModel;
import com.home.urix.PayRoll.View.*;

import java.math.BigDecimal;
import java.math.RoundingMode;
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

    private LanguageEnum getUserLanguageChoice1(){
        LanguageEnum[] enums = LanguageEnum.values();
        while(true) {
            MainView.showLanguageMenu();
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

    private MainMenuEnum getUserMenuChoice1() {
        MainMenuEnum[] enums= MainMenuEnum.values();
        while(true) {
            try {
                MainView.showActionMenu();
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

    private BigDecimal inputSalaryFundWithScanner(Locale locale){
        DecimalFormat df = (DecimalFormat) NumberFormat.getInstance(locale);
        df.setParseBigDecimal(true);
        while(true) {
            MainView.printMessageById(TextConstants.SPECIFY_SALARY_FUND);
            String userInput = scanner.nextLine();
            try {
                BigDecimal bd = ((BigDecimal) df.parseObject(userInput)).setScale(2, RoundingMode.DOWN);
                if(bd.compareTo(BigDecimal.valueOf(0))>=0) {
                    return bd;
                }
                MainView.wrongInputDataMessage();
            } catch (ParseException e) {
                MainView.wrongInputDataMessage();
            }
        }
    }

    public void startProcess(){
        LanguageEnum lang = getUserLanguageChoice();
        Locale locale = new Locale(lang.getLanguage(),lang.getCountry());
        MainView.changeLocale(locale);

        MainView.printMessageById(TextConstants.GREETINGS_MESSAGE);

        MainMenuEnum userChoice;
        while((userChoice = getUserMenuChoice())!= MainMenuEnum.OPTION_EXIT) {
            switch (userChoice){
                case OPTION_SPECIFY_FUND:
                    model.setSalaryFund(inputSalaryFundWithScanner(MainView.getLocale()));
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
                    break;
                case OPTION_BALANCE_ALLOCATION_PLACE:
                    break;
                case OPTION_PAYROLL_PRINT:
                    break;
            }
            //MainView.printString(String.valueOf(model));
            printModelInfo();
        }

        MainView.printMessageById(TextConstants.GOODBYE_MESSAGE);


    }

    private void printModelInfo() {
        MainView.printString(
                "[" +
                model.getSalaryFund() +
                "]"
        );
    }


}
