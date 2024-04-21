package com.app.restapimesen.modules.product.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Immutable;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Immutable
public class ProductCategory {
    private Long id;
    private String name;
}
