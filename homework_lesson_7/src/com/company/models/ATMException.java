package com.company.models;

public class ATMException extends Exception {
    public ATMException(String message) {
        super(message);
    }
}

class InsufficientFundsException extends ATMException {
    public InsufficientFundsException() {
        super("Insufficient funds in ATM.");
    }
}

