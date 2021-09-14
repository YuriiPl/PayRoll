package com.home.urix.PayRoll.Model.Employee;

public enum EmployeeType {
    GENERAL(1),
    MANAGER(2),
    OTHER(4);

    private final int id;
    EmployeeType(int id){
        this.id=id;
    }
    public int value(){
        return id;
    }

    public static EmployeeType typeFromInt(int val){
        for(EmployeeType e : EmployeeType.values()){
            if(val==e.value())return e;
        }
        throw new RuntimeException("EmployeeType unknown ("+val+")");
    }
}
