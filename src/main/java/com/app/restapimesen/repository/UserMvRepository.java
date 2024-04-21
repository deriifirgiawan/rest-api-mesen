package com.app.restapimesen.repository;

import com.app.restapimesen.entity.user.UserMaterializedView;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class UserMvRepository {
    @PersistenceContext
    private EntityManager entityManager;

    public List findAllByStoreId(String storeId) {
        return entityManager.createNativeQuery(
                        "SELECT * FROM users_materialized_view umv WHERE umv.store_id = :storeId and umv.position < 'Owner';",
                        UserMaterializedView.class)
                .setParameter("storeId", storeId)
                .getResultList();
    }
}
