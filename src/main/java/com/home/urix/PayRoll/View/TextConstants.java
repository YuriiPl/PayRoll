package com.home.urix.PayRoll.View;

public enum TextConstants {
    WRONG_INPUT_DATA("input.string.data.wrong"),
    GREETINGS_MESSAGE("start.greeting.message"),
    CHOOSE_LANGUAGE_MESSAGE("choose.your.language"),
    SPECIFY_SALARY_FUND("specify.salary.fund"),
    MENU_HEADER("menu.header"),
    GOODBYE_MESSAGE("goodbye.message");


    private final String value;

    TextConstants(String value){
        this.value = value;
    }

    public String value(){
        return value;
    }
}
