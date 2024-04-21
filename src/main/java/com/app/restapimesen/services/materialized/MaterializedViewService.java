package com.app.restapimesen.services.materialized;

public interface MaterializedViewService {
    void refreshMaterializedView(String viewName);
    void createMaterializedView(String sqlQuery);
}
