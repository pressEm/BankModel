package ru.netckacker.bank.models;

import ru.netckacker.bank.generators.ClientGenerator;

import java.util.ArrayList;
import java.util.List;

public class Bank {
    private Score score;
    private final int N = 5;
    private final int SERVICE_TIME = 10000;
    private final int CLIENTS_PER_MINUTES = 60;
    private List<Operator> operatorList;

    public Bank(int total) {
        this.score = new Score(total);
    }

    //    private final int score = ;
    public void start(){
        operatorList = new ArrayList<>();
        for (int i = 0; i < N; i++) {
            Operator operator = new Operator(i, score);
            operatorList.add(operator);
            Thread t = new Thread(operator);
            t.start();
        }
        ClientGenerator generator = new ClientGenerator(SERVICE_TIME, CLIENTS_PER_MINUTES, operatorList);

        Thread t = new Thread(generator);
        t.start();
    }
}
