package com;

import java.util.Objects;

public class Response {
    String uri;
    Server server;

    public Response(String uri, Server server) {
        this.uri = Objects.requireNonNull(uri, "The uri cannot be null");
        this.server = Objects.requireNonNull(server, "The server cannot be null");
    }

    public String getUri() {
        return uri;
    }

    public Server getServer() {
        return server;
    }
}
