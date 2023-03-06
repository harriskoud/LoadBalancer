package com;

import java.util.Optional;
import java.util.function.Predicate;

public class RoundRobinLoadBalancer implements LoadBalancer {

    @Override
    public Response loadBalance(String request) {
        Optional<Server> server = getServers().stream()
                .filter(Predicate.not(Server::busy))
                .findFirst();

        if (server.isPresent())
            return server.get().handleRequest(request);
        else {
            getRequests().offer(request);
            return null;
        }
    }

    @Override
    public boolean registerServer(Server server) {
        if (server.isRunning())
            return getServers().add(server);
        return false;
    }

    @Override
    public boolean unregisterServer(Server server) {
        if (getServers().contains(server))
            return getServers().remove(server);
        return false;
    }

    @Override
    public synchronized String offerRequest(String request) {
        getRequests().offer(request);
        return request;
    }

    @Override
    public synchronized String removeRequest(String request) {
        getRequests().remove(request);
        return request;
    }
}
