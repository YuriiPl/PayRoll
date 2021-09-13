package com.home.urix.PayRoll.Controller;

public enum RegExpConstants {
    REGEX_WORDS_NUMBERS("words.numbers"),
    REGEXP_NAME("regexp.names"),
    REGEXP_NAME_OPT("regexp.names.opt"),
    REGEXP_DATE("regexp.date");



    private final String name;
    RegExpConstants(String name){
        this.name=name;
    }
    public String value(){
        return name;
    }
}
