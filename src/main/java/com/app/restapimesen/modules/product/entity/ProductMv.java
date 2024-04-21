package com.app.restapimesen.modules.product.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Immutable;

import java.sql.Timestamp;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Immutable
public class ProductMv {
    private String id;
    private String store_id;
    private String name;
    private Long price;
    private String description;
    private Long quantity;
    private String image_url;
    private Timestamp created_at;
    private String user_id;
    private List<ProductCategory> category;
}
