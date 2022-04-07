package ru.netckacker.bank.models;

import ru.netckacker.bank.generators.Action;

import java.time.Duration;

public class Client {
    int id;
    private Action action;
    private int money;
    private Duration serviceTime;

    public Client(int id, Action action, int money, Duration serviceTime) {
        System.out.println("constructor new client " + id);
        this.id = id;
        this.action = action;
        this.money = money;
        this.serviceTime = serviceTime;
    }

    public void setAction(Action action) {
        this.action = action;
    }

    public void setMoney(int money) {
        this.money = money;
    }

    public void setServiceTime(Duration serviceTime) {
        this.serviceTime = serviceTime;
    }

    public Action getAction() {
        return action;
    }

    public int getMoney() {
        return money;
    }

    public Duration getServiceTime() {
        return serviceTime;
    }
}
