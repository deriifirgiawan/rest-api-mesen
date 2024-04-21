package com.app.restapimesen.modules.product.libs;

import com.app.restapimesen.modules.product.entity.ProductCategory;
import com.app.restapimesen.modules.product.entity.ProductMv;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.TypeFactory;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

@Slf4j
public class GenerateListProduct {
    public List<ProductMv> generate(List<Object[]> results) {
        List<ProductMv> products = new ArrayList<>();

        for (Object[] result : results) {
            ProductMv product = new ProductMv();
            product.setId((String) result[0]);
            product.setStore_id((String) result[1]);
            product.setName((String) result[2]);
            product.setPrice((Long) result[3]);
            product.setDescription((String) result[4]);
            product.setQuantity((Long) result[5]);
            product.setImage_url((String) result[6]);
            product.setCreated_at((Timestamp) result[7]);
            product.setUser_id((String) result[8]);

            // Convert category JSON to List<ProductCategory> using ObjectMapper
            ObjectMapper objectMapper = new ObjectMapper();
            TypeFactory typeFactory = objectMapper.getTypeFactory();
            try {
                List<ProductCategory> categories = objectMapper.readValue((String) result[9], ((com.fasterxml.jackson.databind.type.TypeFactory) typeFactory).constructCollectionType(List.class, ProductCategory.class));
                product.setCategory(categories);
            } catch (IOException e) {
                log.error(e.getLocalizedMessage());
            }

            products.add(product);
        }

        return products;
    }

    public List<Integer> generateCategoryToList(String value) {
        List<Integer> results = new ArrayList<>();

        var splitStr = value.split(",");

        for (String string : splitStr) {
            results.add(Integer.valueOf(string));
        }

        return results;
    }
}
