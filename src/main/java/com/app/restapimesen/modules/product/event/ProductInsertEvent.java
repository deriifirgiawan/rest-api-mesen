package com.app.restapimesen.modules.product.event;

import com.app.restapimesen.modules.product.entity.Products;
import lombok.Getter;
import org.springframework.context.ApplicationEvent;

@Getter
public class ProductInsertEvent extends ApplicationEvent {
    private final Products products;

    public ProductInsertEvent(Products products) {
        super(products);
        this.products = products;
    }
}
