package com.home.urix.PayRoll.Model;

import java.math.BigDecimal;

public class MainModel {
    private BigDecimal salaryFund;

    public MainModel(){
        salaryFund=BigDecimal.valueOf(0);
    }

    public BigDecimal getSalaryFund() {
        return salaryFund;
    }

    public void setSalaryFund(BigDecimal salaryFund) {
        this.salaryFund = salaryFund;
    }

}
