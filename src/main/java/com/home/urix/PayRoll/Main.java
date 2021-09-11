package com.home.urix.PayRoll;
//import java.util.Locale;
//import java.util.ResourceBundle;

import com.home.urix.PayRoll.Controller.MainController;

public class Main {
    public static void main(String[] args){
        //ResourceBundle rb = ResourceBundle.getBundle("messages",new Locale("uk","UA"));
        MainController mc = new MainController();
        mc.startProcess();
    }
}
