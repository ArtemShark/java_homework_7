package com.company.models;

public class InvalidDenominationException extends ATMException {
    public InvalidDenominationException() {
        super("Invalid banknote denomination.");
    }
}
