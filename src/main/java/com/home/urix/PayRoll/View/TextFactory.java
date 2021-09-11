package com.home.urix.PayRoll.View;

import java.util.Locale;
import java.util.ResourceBundle;

public class TextFactory {
    static private ResourceBundle messages;
    static private ResourceBundle regexps;
    static {
        messages = ResourceBundle.getBundle("messages");
        regexps = ResourceBundle.getBundle("regexps");
    }


    /**
     * Change locale method
     * @param locale Locale for messages and regular expressions
     * @since 0.0.1
     * @see Locale
     */
    static public void changeLocale(Locale locale){
        messages = ResourceBundle.getBundle("messages",locale);
        regexps = ResourceBundle.getBundle("regexps",locale);
    }

    /**
     * @param str id from resource file
     * @return message according to id in resource file
     */
    static public String getString(String str){
        if(messages.containsKey(str))
            return messages.getString(str);
        return messages.getString("input.string.unknown.id")+str;
    }

    /**
     * @param str id from regexp resource file
     * @return Regular expression according to id in regexp resource file
     * @see java.util.regex.Pattern
     */
    static public String getRegExpString(String str){
        return regexps.getString(str);
    }
}
