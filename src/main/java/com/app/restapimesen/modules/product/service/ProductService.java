package com.app.restapimesen.modules.product.service;

import com.app.restapimesen.modules.product.entity.ProductMv;
import com.app.restapimesen.modules.product.entity.Products;
import com.app.restapimesen.modules.product.models.AddProductRequest;
import com.app.restapimesen.modules.product.models.RequestGetAllProduct;

import java.util.List;

public interface ProductService {
    Products addProduct(AddProductRequest request, String userId);
    List<ProductMv> getAllProductsByStoreId(RequestGetAllProduct request);

    Products updateProductById(AddProductRequest request, String productId);

    String deleteProductById(String productId);
}
