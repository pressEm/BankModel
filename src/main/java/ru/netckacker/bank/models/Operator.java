package ru.netckacker.bank.models;

import ru.netckacker.bank.generators.Action;

import java.util.ArrayDeque;
import java.util.Queue;

public class Operator implements Runnable {
    int id;

    private Queue<Client> clients;
    private Score score;

    public Operator(int id, Score score) {
        clients = new ArrayDeque<>();

        this.id = id;
        this.score = score;
    }

    public Queue<Client> getClients() {
        return clients;
    }

    public synchronized void setClient(Client client) {
        System.out.println("set client-" + client.id + " to operator-" + id + " in " + Thread.currentThread().getName());
        this.clients.add(client);
        notify();
    }

    private void serve(Client client) throws InterruptedException {
        if (client == null) return;
        System.out.println("serve client" + client.id + " " + "by operator-" + this.id + " duration: " + client.getServiceTime().toMillis() + " in " + Thread.currentThread().getName());
        if (client.getAction() == Action.DEPOSIT) {
            score.deposit(client.getMoney());
            System.out.println("client deposit money" + client.getMoney());
            Thread.sleep(client.getServiceTime().toMillis());

        } else {
            if (score.ifEnoughMony(client.getMoney())) {
                score.take(client.getMoney());
                System.out.println("client take money: " + client.getMoney());
                Thread.sleep(client.getServiceTime().toMillis());

            } else {
                System.out.println("client wad refused to withdraw money");
                return;
            }
        }
    }

    @Override
    public void run() {
        System.out.println("operator-" + id + " start in " + Thread.currentThread().getName());
        while (true) {
            if (!clients.isEmpty()) {
                try {
                    Client client = clients.poll();
                    if (client != null) serve(client);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            } else {
                try {
                    synchronized (this) {
                        System.out.println("operator wait...");
                        wait();
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }


//        while (clients.isEmpty()) {
//            try {
//                System.out.println("waiting");
//                synchronized (this){
//                    wait();
//                }
//            }
//            catch (InterruptedException e) {
//            }
//        }
//        while (!clients.isEmpty()) {
//                System.out.println("queue not empty");
//                try {
//                    Client client = clients.poll();
//                    if (client != null) serve(client);
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                }
//        }
//        if (!clients.isEmpty()) {
//            System.out.println("queue not empty");
//            try {
//                Client client = clients.poll();
//                if (client != null) serve(client);
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
//        }else{
//
//        }
//    }
}
//        try {
//            this.wait();
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
//        clients = new ArrayDeque<>();
//        System.out.println("");
////        if (clients.size() > 0) {
//        Client client = clients.poll();
//        try {
//            serve(client);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
////        }
//    }
//}
