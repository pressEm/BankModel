package ru.netckacker.bank;

import ru.netckacker.bank.models.Bank;

public class Main {

    public static void main(String[] args) {
        Bank bank = new Bank(1000);
        bank.start();
    }

}
