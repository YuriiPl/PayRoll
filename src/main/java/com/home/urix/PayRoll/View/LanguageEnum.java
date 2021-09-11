package com.home.urix.PayRoll.View;

public enum LaguageEnum {
    ENGLISH("en",""),
    UKRAINIAN("uk","UA");

    private String language, country;


    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    LaguageEnum(String language, String country) {
        this.language = language;
        this.country = country;
    }
}
