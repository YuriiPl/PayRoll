package com.home.urix.PayRoll.View;

import java.util.Locale;


public interface MainView {

    static void printMessageById(TextConstants id){
        System.out.println(TextFactory.getString(id));
    }

    static void printMenuById(TextMenuEnum id){
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
        for (TextMenuEnum opt : TextMenuEnum.values()) {
            MainView.printMenuById(opt);
        }
    }

    static void showLanguageMenu(){
        MainView.printMessageById(TextConstants.CHOOSE_LANGUAGE_MESSAGE);
        for (LanguageEnum opt : LanguageEnum.values()) {
            MainView.printString(opt.ordinal()+". "+opt.name());
        }
    }

}
