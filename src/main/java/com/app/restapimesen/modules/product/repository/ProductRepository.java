package com.app.restapimesen.modules.product.repository;

import com.app.restapimesen.modules.product.entity.Products;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends JpaRepository<Products, String> {
}
