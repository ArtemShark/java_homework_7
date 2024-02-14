package com.company.models;

import java.util.HashMap;
import java.util.Map;
import java.util.ArrayList;
import java.util.List;

public class ATM {
    private Map<Integer, Integer> cashInventory;
    private final int MIN_WITHDRAWAL_AMOUNT;
    private final int MAX_BANKNOTES;

    public ATM(int minWithdrawalAmount, int maxBanknotes) {
        this.cashInventory = new HashMap<>();
        this.MIN_WITHDRAWAL_AMOUNT = minWithdrawalAmount;
        this.MAX_BANKNOTES = maxBanknotes;
    }

    public void initializeATM(Map<Integer, Integer> initialCash) {
        cashInventory.putAll(initialCash);
    }

    public void addCash(int denomination, int quantity) throws InvalidDenominationException {
        int[] validDenominations = {1, 2, 5, 10, 20, 50, 100, 200, 500};
        boolean isValidDenomination = false;

        for (int validDenomination : validDenominations) {
            if (denomination == validDenomination) {
                isValidDenomination = true;
                break;
            }
        }

        if (!isValidDenomination) {
            throw new InvalidDenominationException();
        }

        cashInventory.put(denomination, cashInventory.getOrDefault(denomination, 0) + quantity);
    }


    public int getTotalCash() {
        return cashInventory.entrySet().stream()
                .mapToInt(entry -> entry.getKey() * entry.getValue())
                .sum();
    }

    public void withdrawCash(int amount) throws ATMException {
        if (amount < MIN_WITHDRAWAL_AMOUNT) {
            throw new ATMException("Requested amount is less than the minimum withdrawal limit.");
        }
        if (getTotalCash() < amount) {
            throw new InsufficientFundsException();
        }

        Map<Integer, Integer> tempInventory = new HashMap<>(cashInventory);
        Map<Integer, Integer> toWithdraw = new HashMap<>();

        int remainingAmount = amount;
        int totalNotes = 0;

        List<Integer> denominations = new ArrayList<>(cashInventory.keySet());
        denominations.sort((a, b) -> b - a);

        for (int denomination : denominations) {
            int noteCount = remainingAmount / denomination;
            int availableNotes = tempInventory.get(denomination);

            if (noteCount > 0 && availableNotes > 0) {
                int notesToWithdraw = Math.min(noteCount, availableNotes);
                if (totalNotes + notesToWithdraw > MAX_BANKNOTES) {
                    break;
                }
                toWithdraw.put(denomination, notesToWithdraw);
                totalNotes += notesToWithdraw;
                remainingAmount -= denomination * notesToWithdraw;
                tempInventory.put(denomination, availableNotes - notesToWithdraw);
            }
        }

        if (remainingAmount > 0) {
            throw new ATMException("Unable to dispense the exact amount with the available denominations.");
        }

        cashInventory = tempInventory;

        System.out.println("Dispensed: " + amount + ". Banknotes: " + toWithdraw);
    }

}

