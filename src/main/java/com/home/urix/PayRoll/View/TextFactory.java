package com.home.urix.PayRoll.View;

import com.home.urix.PayRoll.Controller.RegExpConstants;

import java.util.Locale;
import java.util.ResourceBundle;

public class TextFactory {
    static private ResourceBundle messages;
    static private ResourceBundle regexps;
    static private ResourceBundle menuMessages;
    static private Locale locale;

    static private final String MESSAGES_BUNDLE="messages";
    static private final String REGEXPS_BUNDLE="regexps";
    static private final String MENU_MESSAGES_BUNDLE="menumessages";

    static {
        changeLocale(Locale.ENGLISH);
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
        locale = newLocale;
        messages = ResourceBundle.getBundle(MESSAGES_BUNDLE,locale);
        regexps = ResourceBundle.getBundle(REGEXPS_BUNDLE,locale);
        menuMessages = ResourceBundle.getBundle(MENU_MESSAGES_BUNDLE,locale);
    }

    /**
     * @param textEnum id from resource file
     * @return message according to id in resource file
     */
    static public String getString(TextConstants textEnum){
        if(messages.containsKey(textEnum.value()))
            return messages.getString(textEnum.value());
        return messages.getString("input.string.unknown.id")+textEnum.value();
    }

    /**
     * @param regExpEnum id from regexp resource file
     * @return Regular expression according to id in regexp resource file
     * @see java.util.regex.Pattern
     */
    static public String getRegExpString(RegExpConstants regExpEnum){
        if(regexps.containsKey(regExpEnum.value()))
            return regexps.getString(regExpEnum.value());
        return messages.getString("input.string.unknown.id")+regExpEnum.value();
    }

    /**
     * @param menuEnum id from regexp resource file
     * @return Regular expression according to id in regexp resource file
     * @see java.util.regex.Pattern
     */
    static public String getMenuString(TextMenuEnum menuEnum){
        if(menuMessages.containsKey(menuEnum.value()))
            return menuMessages.getString(menuEnum.value());
        return messages.getString("input.string.unknown.id")+menuEnum.value();
    }
}
