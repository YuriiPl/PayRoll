package com.home.urix.PayRoll.View;

import java.util.Locale;


public interface MainView {

    static void printMessageById(TextConstants id){
        System.out.println(TextFactory.getString(id));
    }

    static void printMenuById(TextMenuConstant id){
        System.out.println(id.ordinal()+". "+TextFactory.getMenuString(id));
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
        for (TextMenuConstant opt : TextMenuConstant.values()) {
            MainView.printMenuById(opt);
        }
    }

}
