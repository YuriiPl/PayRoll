package com.home.urix.PayRoll.View;

public enum TextMenuConstant {
    OPTION_SPECIFY_FUND("option.specify.fund"),
    OPTION_BALANCE_ALLOCATION_TYPE("option.balance.allocation.type"),
    OPTION_BALANCE_ALLOCATION_PLACE("option.balance.allocation.place"),
    OPTION_ADD_EMPLOYEE("option.add.employee"),
    OPTION_SHOW_EMPLOYEES("option.show.employees"),
    OPTION_EXIT("option.exit");

    private final String name;

    TextMenuConstant(String name){
        this.name=name;
    }

    public String value(){
        return name;
    }
}
