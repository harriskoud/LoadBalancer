package com;

import java.util.LinkedList;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public interface LoadBalancer {

    BlockingQueue<String> requests = new LinkedBlockingQueue<>();
    LinkedList<Server> availableServers = new LinkedList<>();

    Response loadBalance(String request);

    boolean registerServer(Server server);

    boolean unregisterServer(Server server);

    String offerRequest(String request);

    String removeRequest(String request);

    default LinkedList<Server> getServers() {
        return availableServers;
    }

    default BlockingQueue<String> getRequests() {
        return requests;
    }

}
