package com.home.urix.PayRoll.Controller;

public enum RegExpConstants {
    REGEX_WORDS_NUMBERS("words.numbers");

    private final String name;

    RegExpConstants(String name){
        this.name=name;
    }

    public String value(){
        return name;
    }
}
