package com.home.urix.PayRoll.Controller;

import com.home.urix.PayRoll.Model.MainModel;
import com.home.urix.PayRoll.View.MainView;
import com.home.urix.PayRoll.View.TextConstants;
import com.home.urix.PayRoll.View.TextFactory;
import com.home.urix.PayRoll.View.TextMenuConstant;

import java.math.BigDecimal;
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

    private BigDecimal inputSalaryFundWithScanner(Locale locale){
        DecimalFormat df = (DecimalFormat) NumberFormat.getInstance(locale);
        df.setParseBigDecimal(true);
        while(true) {
            MainView.printMessageById(TextConstants.SPECIFY_SALARY_FUND);
            String userInput = scanner.nextLine();
            try {
                return (BigDecimal) df.parseObject(userInput);
            } catch (ParseException e) {
                MainView.wrongInputDataMessage();
            }
        }
    }

    private TextMenuConstant getUserMenuChoice() {
        TextMenuConstant[] enums=TextMenuConstant.values();
        while(true) {
            try {
                MainView.showActionMenu();
                int userChoice = Integer.parseInt(scanner.nextLine());
                if(userChoice >= 0 && userChoice < enums.length){
                    return enums[userChoice];
                }
                throw new NumberFormatException("Wrong number!");
            } catch (NumberFormatException e){
                MainView.wrongInputDataMessage();
            }
        }
    }

    public void startProcess(){
        String choose = getStringFromScanner(TextConstants.CHOOSE_LANGUAGE_MESSAGE,RegExpConstants.CHOOSE_LANGUAGE);
        switch (choose){
            case "1":
                Locale locale = new Locale("uk","UA");
                MainView.changeLocale(locale);
                break;
            case "2":
                MainView.changeLocale(Locale.ENGLISH);
                break;
            default:

        }

        MainView.printMessageById(TextConstants.GREETINGS_MESSAGE);

        TextMenuConstant userChoice;
        while((userChoice= getUserMenuChoice())!=TextMenuConstant.OPTION_EXIT){
            switch (userChoice){
                case OPTION_SPECIFY_FUND:
                    model.setSalaryFund(inputSalaryFundWithScanner(MainView.getLocale()));
                    break;
                case OPTION_BALANCE_ALLOCATION_TYPE:
                    break;
                case OPTION_BALANCE_ALLOCATION_PLACE:
                    break;
                case OPTION_ADD_EMPLOYEE:
                    break;
            }
        }




    }



}
