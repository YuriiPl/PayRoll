package com.home.urix.PayRoll.View;

import java.util.Locale;


public interface MainView {

    static void printMessageById(TextConstants id){
        System.out.println(TextFactory.getString(id));
    }

    static <T extends Enum<T>> void printMenuById(T id){
        System.out.println(id.ordinal()+". "+TextFactory.getMenuString(id));
    }

    static void printString(String str){
        System.out.println(str);
    }

    static void wrongInputDataMessage() {
        System.out.println(TextFactory.getString(TextConstants.WRONG_INPUT_DATA));
    }

    static void changeLocale(Locale locale){
        TextFactory.changeLocale(locale);
    }

    static Locale getLocale() {
        return TextFactory.getLocale();
    }

    static void showActionMenu(){
        MainView.printMessageById(TextConstants.MENU_HEADER);
        for (MainMenuEnum opt : MainMenuEnum.values()) {
            MainView.printMenuById(opt);
        }
    }

    static void showLanguageMenu(){
        MainView.printMessageById(TextConstants.CHOOSE_LANGUAGE_MESSAGE);
        for (LanguageEnum opt : LanguageEnum.values()) {
            MainView.printMenuById(opt);
        }
    }

    static void showEmployeesMenu(){
        MainView.printMessageById(TextConstants.MENU_EMPLOYEES_HEADER);
        for (EmployeeMenuEnum opt : EmployeeMenuEnum.values()) {
            MainView.printMenuById(opt);
        }
    }

    static void showBalanceAllocationTypeMenu(){
        MainView.printMessageById(TextConstants.MENU_BALANCE_ALLOCATION_TYPE);
        for (BalanceAllocationTypeEnum opt : BalanceAllocationTypeEnum.values()) {
            MainView.printMenuById(opt);
        }
    }

    static void showBalanceAllocationPlaceMenu(){
        MainView.printMessageById(TextConstants.MENU_BALANCE_ALLOCATION_PLACE);
        for (BalanceAllocationPlaceEnum opt : BalanceAllocationPlaceEnum.values()) {
            MainView.printMenuById(opt);
        }
    }

}
