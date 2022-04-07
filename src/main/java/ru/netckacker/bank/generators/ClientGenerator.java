package ru.netckacker.bank.generators;

import ru.netckacker.bank.models.Client;
import ru.netckacker.bank.models.Operator;
import ru.netckacker.bank.models.Score;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Random;

public class ClientGenerator implements Runnable {
    private int SERVICE_TIME;
    private int CLIENTS_PER_MINUTES;
    private Score score;
    private List<Operator> operatorList;
    private Random random = new Random();

    public ClientGenerator(int SERVICE_TIME, int CLIENTS_PER_MINUTES, List<Operator> operatorList) {
        this.SERVICE_TIME = SERVICE_TIME;
        this.CLIENTS_PER_MINUTES = CLIENTS_PER_MINUTES;
        this.operatorList = operatorList;
    }


    private Client newClient(int id) {
        double maxDur = (SERVICE_TIME * 1.5);
        double minDur = (SERVICE_TIME * 0.5);
        double duration = (Math.random() * ((maxDur - minDur) + 1)) + minDur;
        Action action;
        if ((Math.random() < 0.5 ? 0 : 1) == 1) {
            action = Action.DEPOSIT;
        } else {
            action = Action.TAKE;
        }
        double maxMoney = (10000);
        double minMoney = (10);
        int money = (int) ((Math.random() * ((maxMoney - minMoney) + 1)) + minMoney);

        return new Client(id, action, money, Duration.ofMillis((long)duration));
    }


    private Operator findMinQueue(){
        int min = -1;
        Operator operator = null;
        for (int i = 0; i < operatorList.size(); i++) {
            if (operatorList.get(i).getClients().size() < min || min == -1){
                min = operatorList.get(i).getClients().size();
                operator = operatorList.get(i);
            }
        }
        System.out.println("min " + min);
        return operator;
    }

    @Override
    public void run() {
        int totalDelayMillis = 60000;
        for (int i = 0; i < CLIENTS_PER_MINUTES; i++) {
            try {
                int actualDelay;
                if (2 * totalDelayMillis / (CLIENTS_PER_MINUTES - 1) > 10) {
                    actualDelay = random.nextInt(2 * totalDelayMillis / (CLIENTS_PER_MINUTES - 1));
                } else {
                    actualDelay = totalDelayMillis;
                }
                if (i < CLIENTS_PER_MINUTES - 1) {
                    Thread.sleep(actualDelay);
                    totalDelayMillis -= actualDelay;
                } else {
                    Thread.sleep(totalDelayMillis);
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            Client client = newClient(i);
            Operator operator = findMinQueue();
            operator.setClient(client);
            synchronized (operator){
                operator.notify();
            }
        }
    }
}
