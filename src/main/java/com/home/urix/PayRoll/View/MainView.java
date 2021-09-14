package com.home.urix.PayRoll.View;

import java.util.Locale;


public interface MainView {

    static void printMessageById(TextConstants id){
        System.out.println(TextFactory.getString(id));
    }

    static String getMessageById(TextConstants id){
        return TextFactory.getString(id);
    }

    static <T extends Enum<T>> void printMenuById(T id){
        System.out.println(id.ordinal()+". "+TextFactory.getMenuString(id));
    }

    static void printString(String str){
        System.out.println(str);
    }

    static void changeLocale(Locale locale){
        TextFactory.changeLocale(locale);
    }

    static Locale getLocale() {
        return TextFactory.getLocale();
    }

    static void wrongInputDataMessage() {
        System.out.println(TextFactory.getString(TextConstants.WRONG_INPUT_DATA));
    }
}
