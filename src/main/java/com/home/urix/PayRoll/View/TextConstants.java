package com.home.urix.PayRoll.View;

public enum TextConstants {
    WRONG_INPUT_DATA("input.string.data.wrong"),
    GREETINGS_MESSAGE("start.greeting.message"),
    CHOOSE_LANGUAGE_MESSAGE("choose.your.language"),
    SPECIFY_SALARY_FUND("specify.salary.fund"),
    MENU_HEADER("menu.header");



    private final String name;

    TextConstants(String name){
        this.name=name;
    }

    public String value(){
        return name;
    }
}
