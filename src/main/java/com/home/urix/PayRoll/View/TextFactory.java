package com.home.urix.PayRoll.View;

import com.home.urix.PayRoll.Controller.RegExpConstants;

import java.util.Locale;
import java.util.ResourceBundle;

public class TextFactory {
    static private ResourceBundle messages;
    static private ResourceBundle regexps;
    static private Locale locale;

    static {
        messages = ResourceBundle.getBundle("messages");
        regexps = ResourceBundle.getBundle("regexps");
        locale = Locale.ENGLISH;
    }

    public static Locale getLocale() {
        return locale;
    }

    /**
     * Change locale method
     * @param newLocale Locale for messages and regular expressions
     * @since 0.0.1
     * @see Locale
     */
    static public void changeLocale(Locale newLocale){
        messages = ResourceBundle.getBundle("messages",locale);
        regexps = ResourceBundle.getBundle("regexps",locale);
        locale = newLocale;
    }

    /**
     * @param str id from resource file
     * @return message according to id in resource file
     */
    static public String getString(TextConstants str){
        if(messages.containsKey(str.value()))
            return messages.getString(str.value());
        return messages.getString("input.string.unknown.id")+str.value();
    }

    /**
     * @param regExp id from regexp resource file
     * @return Regular expression according to id in regexp resource file
     * @see java.util.regex.Pattern
     */
    static public String getRegExpString(RegExpConstants regExp){
        return regexps.getString(regExp.value());
    }
}
