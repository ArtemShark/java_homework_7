package com.company.models;

import java.util.ArrayList;
import java.util.List;

public class Bank {
    private List<ATM> atms;

    public Bank() {
        this.atms = new ArrayList<>();
    }

    public void addATM(ATM atm) {
        atms.add(atm);
    }

    public int getTotalCashInATMs() {
        return atms.stream().mapToInt(ATM::getTotalCash).sum();
    }
}

