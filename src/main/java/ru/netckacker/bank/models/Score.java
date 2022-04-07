package ru.netckacker.bank.models;

public class Score {
    private int total;

    public Score(int total) {
        this.total = total;
    }

    synchronized public void deposit(int total) {
        this.total += total;
    }

    synchronized public void take(int total) {
        this.total -= total;
    }

    synchronized public int getTotal() {
        return total;
    }

    public boolean ifEnoughMony(int total) {
        return this.total >= total;
    }
}
