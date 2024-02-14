package com.company;

import com.company.models.ATM;
import com.company.models.Bank;
import com.company.models.ATMException;
import com.company.models.InvalidDenominationException;
import java.util.Map;
import java.util.HashMap;
public class Main {
    public static void main(String[] args) {

        Bank bank = new Bank();
        ATM atm1 = new ATM(100, 10); // Минимальная сумма для снятия - 100, максимум купюр - 10
        ATM atm2 = new ATM(100, 20);

        Map<Integer, Integer> initialCashATM1 = new HashMap<>();
        initialCashATM1.put(500, 2);
        initialCashATM1.put(200, 5);
        initialCashATM1.put(100, 10);
        atm1.initializeATM(initialCashATM1);

        Map<Integer, Integer> initialCashATM2 = new HashMap<>();
        initialCashATM2.put(500, 1);
        initialCashATM2.put(100, 5);
        initialCashATM2.put(50, 10);
        atm2.initializeATM(initialCashATM2);

        bank.addATM(atm1);
        bank.addATM(atm2);


        System.out.println("Total cash in all ATMs: " + bank.getTotalCashInATMs());

        // Добавление валидных номиналов в банкоматы
        try {
            atm1.addCash(50, 2);
            System.out.println("Successfully added cash to ATM 1.");
        } catch (InvalidDenominationException e) {
            System.out.println("Failed to add cash to ATM 1: " + e.getMessage());
        }

        // Попытка добавить невалидный номинал
        try {
            atm2.addCash(3, 10);
        } catch (InvalidDenominationException e) {
            System.out.println("Failed to add cash to ATM 2: Invalid denomination.");
        }

        // Попытка снять деньги из первого банкомата
        try {
            atm1.withdrawCash(1400); // Успешное снятие
            System.out.println("Successfully withdrawn from ATM 1");
        } catch (ATMException e) {
            System.out.println(e.getMessage());
        }

        // Попытка снять слишком большую сумму
        try {
            atm1.withdrawCash(5000);
        } catch (ATMException e) {
            System.out.println("Failed to withdraw from ATM 1: " + e.getMessage());
        }

        // Попытка снять сумму, превышающую максимально допустимое количество банкнот
        try {
            atm2.withdrawCash(1500);
        } catch (ATMException e) {
            System.out.println("Failed to withdraw from ATM 2: " + e.getMessage());
        }

        System.out.println("Total cash in all ATMs after withdrawals: " + bank.getTotalCashInATMs());
    }
}
