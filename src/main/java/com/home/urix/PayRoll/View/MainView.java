package com.home.urix.PayRoll.View;

import java.util.Locale;


public interface MainView {

    static void printMessageById(TextConstants id){
        System.out.println(TextFactory.getString(id));
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

}
