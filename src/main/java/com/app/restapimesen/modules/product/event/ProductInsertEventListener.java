package com.app.restapimesen.modules.product.event;

import com.app.restapimesen.services.materialized.MaterializedViewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
public class ProductInsertEventListener {
    @Autowired
    private MaterializedViewService materializedViewService;

    @EventListener
    public void handleUserInsertEvent(ProductInsertEvent event) {
        materializedViewService.refreshMaterializedView("products_materialized_view");
    }
}
