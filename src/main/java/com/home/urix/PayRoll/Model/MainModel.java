package com.home.urix.PayRoll.Model;

import com.home.urix.PayRoll.Model.Database.DataBase;
import com.home.urix.PayRoll.Model.Database.SQLiteDb;

import java.math.BigDecimal;
import java.sql.SQLException;

public class MainModel {
    private BigDecimal salaryFund;
    private final DataBase db;

    public MainModel(){
        salaryFund=BigDecimal.valueOf(0);
        db = new SQLiteDb();
    }

    public void connectToDatabase(String dbName) throws SQLException, ClassNotFoundException {
        db.open(dbName);
    }

    public BigDecimal getSalaryFund() {
        return salaryFund;
    }

    public void setSalaryFund(BigDecimal salaryFund) {
        this.salaryFund = salaryFund;
    }

}
