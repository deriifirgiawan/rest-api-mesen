package com.app.restapimesen.events.user;

import com.app.restapimesen.services.materialized.MaterializedViewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
public class UserInsertEventListener {
    @Autowired
    private MaterializedViewService materializedViewService;

    @EventListener
    public void handleUserInsertEvent(UserInsertEvent event) {
        materializedViewService.refreshMaterializedView("users_materialized_view");
    }
}
