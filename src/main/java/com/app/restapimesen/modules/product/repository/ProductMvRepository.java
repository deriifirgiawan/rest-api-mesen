package com.app.restapimesen.modules.product.repository;

import com.app.restapimesen.modules.product.entity.ProductMv;
import com.app.restapimesen.modules.product.libs.GenerateListProduct;
import com.app.restapimesen.modules.product.models.RequestGetAllProduct;
import com.app.restapimesen.modules.product.models.TypePrice;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Slf4j
@RequiredArgsConstructor
public class ProductMvRepository {

    @Autowired
    private final GenerateListProduct generateListProduct;

    @PersistenceContext
    private EntityManager entityManager;

    public List<ProductMv> findAllProductByStoreId(RequestGetAllProduct request) {
        String query = "SELECT * FROM products_materialized_view pmv WHERE pmv.store_id = :storeId";
        List<Object[]> results = entityManager.createNativeQuery(query)
                .setParameter("storeId", request.getStoreId())
                .getResultList();
        return generateListProduct.generate(results);
    }

    public List<ProductMv> findAllProductByOrder(RequestGetAllProduct request) {
        String query = "SELECT * FROM products_materialized_view pmv WHERE pmv.store_id = :storeId ORDER BY pmv.name " + request.getOrder().get().getName();
        List<Object[]> results = entityManager.createNativeQuery(query)
                .setParameter("storeId", request.getStoreId())
                .getResultList();
        return generateListProduct.generate(results);
    }

    public List<ProductMv> findAllProductsByCategories(RequestGetAllProduct request) {
        String query =
                "SELECT * FROM products_materialized_view pmv WHERE pmv.store_id = :storeId AND EXISTS (SELECT 1 FROM json_array_elements(category) as cat WHERE CAST(cat->>'id' AS integer) IN (:categoryIds))";
        List<Object[]> results = entityManager.createNativeQuery(query)
                .setParameter("storeId", request.getStoreId())
                .setParameter("categoryIds", request.getCategories().get())
                .getResultList();


        return generateListProduct.generate(results);
    }

    public List<ProductMv> findAllProductsByCategoriesWithOrder(RequestGetAllProduct request) {
        List<Object[]> results = null;
        if (request.getCategories().isPresent() && request.getOrder().isPresent()) {
            String query =
                    "SELECT * FROM products_materialized_view pmv WHERE pmv.store_id = :storeId AND EXISTS (SELECT 1 FROM json_array_elements(category) as cat WHERE CAST(cat->>'id' AS integer) IN (:categoryIds)) ORDER BY pmv.name " + request.getOrder().get().getName();
            results = entityManager.createNativeQuery(query)
                    .setParameter("storeId", request.getStoreId())
                    .setParameter("categoryIds", request.getCategories().get())
                    .getResultList();
        }
        
        if (request.getCategories().isPresent() && request.getPrice().isPresent()) {
            String query = getQuery(request);
            results = entityManager.createNativeQuery(query)
                    .setParameter("storeId", request.getStoreId())
                    .setParameter("categoryIds", request.getCategories().get())
                    .getResultList();
        }

        return generateListProduct.generate(results);
    }

    private static String getQuery(RequestGetAllProduct request) {
        String endQuery;
        if (request.getPrice().get() == TypePrice.HIGHEST) {
            endQuery = "order by pmv.price desc";
        } else {
            endQuery = "order by pmv.price asc";
        }
        String query = "SELECT * FROM products_materialized_view pmv WHERE pmv.store_id = :storeId AND EXISTS (SELECT 1 FROM json_array_elements(category) as cat WHERE CAST(cat->>'id' AS integer) IN (:categoryIds)) " + endQuery;
        return query;
    }

    public List<ProductMv> findAllProductOrderByPrice(RequestGetAllProduct request) {
        String query;
        if (request.getPrice().get() == TypePrice.HIGHEST) {
            query = "select * from products_materialized_view pmv where pmv.store_id = :storeId order by pmv.price desc";
        } else {
            query = "select * from products_materialized_view pmv where pmv.store_id = :storeId order by pmv.price asc";
        }
        List<Object[]> results = entityManager.createNativeQuery(query)
                .setParameter("storeId", request.getStoreId())
                .getResultList();
        return generateListProduct.generate(results);
    }
}
