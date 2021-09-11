package com.home.urix.PayRoll.View;

public enum TextMenuEnum {
    OPTION_SPECIFY_FUND("option.specify.fund"),
    OPTION_ADD_EMPLOYEE("option.add.employee"),
    OPTION_SHOW_EMPLOYEES("option.show.employees"),
    OPTION_BALANCE_ALLOCATION_TYPE("option.balance.allocation.type"),
    OPTION_BALANCE_ALLOCATION_PLACE("option.balance.allocation.place"),
    OPTION_PAYROLL_PRINT("option.payroll.print"),
    OPTION_EXIT("option.exit");

    private final String value;

    TextMenuEnum(String value){
        this.value =value;
    }

    public String value(){
        return value;
    }
}
