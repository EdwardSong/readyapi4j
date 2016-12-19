package com.smartbear.readyapi4j.testserver.execution;

public enum Scheme {
    HTTP("http"),
    HTTPS("https");

    private String value;

    Scheme(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}