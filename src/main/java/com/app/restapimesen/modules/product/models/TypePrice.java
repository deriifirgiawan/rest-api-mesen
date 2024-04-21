package com.app.restapimesen.modules.product.models;

public enum TypePrice {
    HIGHEST("desc"),
    LOWEST("asc");

    private final String name;

    TypePrice(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
