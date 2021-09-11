package com.home.urix.PayRoll.View;

import java.util.Locale;
import java.util.ResourceBundle;

public interface MainView {
    static void printMessageById(String stringId){
        System.out.println(TextFactory.getString(stringId));
    }

    static void inputWrongDataMessage() {
        System.out.println(TextFactory.getString("input.string.data.wrong"));
    }

    static public void changeLocale(Locale locale){
        TextFactory.changeLocale(locale);
    }

}
