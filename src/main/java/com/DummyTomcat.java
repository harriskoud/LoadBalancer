package com;

import java.util.logging.Logger;

public class DummyTomcat extends AbstractServer {

    private static Logger LOG = Logger.getLogger("DummyTomcat");

    public DummyTomcat(String name) {
        super.name = name;
        start();
    }

}