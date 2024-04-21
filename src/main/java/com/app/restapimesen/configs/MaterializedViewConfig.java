package com.app.restapimesen.configs;

import com.app.restapimesen.services.materialized.MaterializedViewService;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class MaterializedViewConfig {
    @Autowired
    private MaterializedViewService materializedViewService;

    @PostConstruct
    public void init() {
        materializedViewService.createMaterializedView("CREATE MATERIALIZED VIEW IF NOT EXISTS users_materialized_view AS SELECT u.id, u.name, u.email, u.position, u.store_id FROM users u");
        materializedViewService.createMaterializedView(
                "CREATE MATERIALIZED VIEW IF NOT EXISTS products_materialized_view AS SELECT \n" +
                        "\tp.id as id,\n" +
                        "    p.store_id as store_id,\n" +
                        "    p.name AS name,\n" +
                        "    p.price AS price,\n" +
                        "    p.description as description,\n" +
                        "    p.quantity as quantity,\n" +
                        "    p.image_url as image_url,\n" +
                        "    p.created_at as created_at,\n" +
                        "    p.user_id as user_id,\n" +
                        "    json_agg(\n" +
                        "        json_build_object(\n" +
                        "            'id', c.id,\n" +
                        "            'name', c.name\n" +
                        "        )\n" +
                        "    ) AS category\n" +
                        "FROM \n" +
                        "    products p\n" +
                        "JOIN \n" +
                        "    product_category pc ON p.id = pc.product_id\n" +
                        "JOIN \n" +
                        "    categories c ON pc.category_id = c.id\n" +
                        "GROUP BY p.id, p.store_id, p.\"name\", p.price, p.description, p.quantity, p.image_url, p.created_at, p.user_id;\n "
        );

        materializedViewService.refreshMaterializedView("users_materialized_view");
        materializedViewService.refreshMaterializedView("products_materialized_view");
    }
}
