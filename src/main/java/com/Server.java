package com;

public interface Server {

    void start();

    void stop();

    boolean isRunning();

    boolean busy();

    Response handleRequest(String request);

    String getName();

}
