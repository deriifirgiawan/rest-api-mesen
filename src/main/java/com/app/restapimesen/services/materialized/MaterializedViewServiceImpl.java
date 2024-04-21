package com.app.restapimesen.services.materialized;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

@Service
public class MaterializedViewServiceImpl implements MaterializedViewService {
    @PersistenceContext
    private EntityManager entityManager;

    @Transactional
    @Override
    public void createMaterializedView(String sqlQuery) {
        entityManager.createNativeQuery(sqlQuery).executeUpdate();
    }

    @Transactional
    @Override
    public void refreshMaterializedView(String viewName) {
        entityManager.createNativeQuery("REFRESH MATERIALIZED VIEW " + viewName).executeUpdate();
    }
}
