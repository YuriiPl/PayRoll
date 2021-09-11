package com.home.urix.PayRoll.Controller;

public enum RegExpConstants {
    CHOOSE_LANGUAGE("choose.language");

    private final String name;

    RegExpConstants(String name){
        this.name=name;
    }

    public String value(){
        return name;
    }
}
