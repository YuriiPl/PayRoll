package com.home.urix.PayRoll.View;

public enum LanguageEnum {
    ENGLISH("en","US"),
    UKRAINIAN("uk","UA");

    private String language, country;

    public String getLanguage() {
        return language;
    }

    public String getCountry() {
        return country;
    }

    LanguageEnum(String language, String country) {
        this.language = language;
        this.country = country;
    }
}
