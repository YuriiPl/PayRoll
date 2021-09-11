package com.home.urix.PayRoll.Controller;

import com.home.urix.PayRoll.Model.MainModel;
import com.home.urix.PayRoll.View.MainView;
import com.home.urix.PayRoll.View.TextConstants;
import com.home.urix.PayRoll.View.TextFactory;

import java.util.Locale;
import java.util.Scanner;

public class MainController {
    MainView view;
    MainModel model;
    Scanner scanner;

    public MainController(){
        scanner = new Scanner(System.in);
    }

    private String getStringFromScanner(String messageResourceBundleId, String regexpResourceBundleId){
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
        String choose = getStringFromScanner(TextConstants.CHOOSE_LANGUAGE_MESSAGE,RegExpConstants.CHOOSE_LANGUAGE);
        Locale locale;
        switch (choose){
            case "1": locale = new Locale("uk","UA"); break;
            default:  locale = Locale.ENGLISH;
        }
        MainView.changeLocale(locale);
        MainView.printMessageById(TextConstants.GREETINGS_MESSAGE);

    }

}
