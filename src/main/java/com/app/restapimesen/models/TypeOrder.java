package com.app.restapimesen.models;

public enum TypeOrder {
    ASC("asc"),
    DESC("desc");

    private final String name;

    TypeOrder(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
