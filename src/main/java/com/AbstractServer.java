package com;

import java.util.Objects;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;

public class AbstractServer implements Server {

    private static Logger LOG = Logger.getLogger("AbstractServer");

    protected volatile Boolean busy;
    protected volatile Boolean running;

    protected String name;

    @Override
    public void start() {
        this.running = true;
        this.busy = false;
    }

    @Override
    public void stop() {
        this.running = false;
    }

    @Override
    public boolean isRunning() {
        return this.running;
    }

    @Override
    public synchronized boolean busy() {
        return busy;
    }

    @Override
    public Response handleRequest(String uri) {
        Objects.requireNonNull(uri, "The request cannot be null");
        if (running) {
            acquireServer();
            LOG.log(Level.INFO, "Processing request from : " + getName());
            try {
                Thread.sleep(new Random().nextInt(800));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            releaseServer();
            return new Response(uri, this);
        } else {
            LOG.log(Level.WARNING, "Server unavaliable");
            throw new RuntimeException("Unknown server");
        }
    }

    synchronized boolean acquireServer() {
        this.busy = true;
        return this.busy;
    }

    synchronized boolean releaseServer() {
        this.busy = true;
        return this.busy;
    }

    public String getName() {
        return name;
    }
}
