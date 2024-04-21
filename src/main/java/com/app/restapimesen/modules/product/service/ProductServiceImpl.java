package com.app.restapimesen.modules.product.service;

import com.app.restapimesen.modules.product.entity.Category;
import com.app.restapimesen.modules.product.entity.ProductMv;
import com.app.restapimesen.modules.product.entity.Products;
import com.app.restapimesen.modules.product.event.ProductInsertEvent;
import com.app.restapimesen.modules.product.models.AddProductRequest;
import com.app.restapimesen.modules.product.models.RequestGetAllProduct;
import com.app.restapimesen.modules.product.repository.CategoryRepository;
import com.app.restapimesen.modules.product.repository.ProductMvRepository;
import com.app.restapimesen.modules.product.repository.ProductRepository;
import com.app.restapimesen.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService{
    private final ProductRepository repository;
    private final ProductMvRepository productMvRepository;
    private final UserRepository userRepository;
    private final CategoryRepository categoryRepository;


    @Autowired
    private final ApplicationEventPublisher applicationEventPublisher;

    @Override
    public Products addProduct(AddProductRequest request, String userId) {
        var user = userRepository.findById(userId);
        List<Category> categories = new ArrayList<>();

        if (user.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User with id: " + userId + " Not Found.");
        }

        for (var i = 0; i < request.getCategoriesId().size(); i++) {
            categories.add(categoryRepository.findById(request.getCategoriesId().get(i)).orElseThrow());
        }

        var product = Products.builder()
                .name(request.getName())
                .description(request.getDescription())
                .price(request.getPrice())
                .quantity(request.getQuantity())
                .users(user.get())
                .createdAt(new Timestamp(new Date(System.currentTimeMillis()).getTime()))
                .imageUrl(request.getImage_url())
                .categories(categories)
                .stores(user.get().getStores())
                .build();

        repository.save(product);

        applicationEventPublisher.publishEvent(new ProductInsertEvent(product));

        return product;
    }

    @Override
    public List<ProductMv> getAllProductsByStoreId(RequestGetAllProduct request) {
        boolean hasCategories = request.getCategories().isPresent();
        boolean hasOrder = request.getOrder().isPresent();
        boolean hasPrice = request.getPrice().isPresent();

        if (hasCategories && hasOrder) {
            return productMvRepository.findAllProductsByCategoriesWithOrder(request);
        }

        if (hasCategories && hasPrice) {
            return productMvRepository.findAllProductsByCategoriesWithOrder(request);
        }

        if (hasCategories) {
            return productMvRepository.findAllProductsByCategories(request);
        }

        if (hasOrder) {
            return productMvRepository.findAllProductByOrder(request);
        }

        if (hasPrice) {
            return productMvRepository.findAllProductOrderByPrice(request);
        }

        return productMvRepository.findAllProductByStoreId(request);
    }


    @Override
    public Products updateProductById(AddProductRequest request, String productId) {
        var product = repository.findById(productId);
        List<Category> categories = new ArrayList<>();

        if (product.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Product Not Found");
        }


        for (var i = 0; i < request.getCategoriesId().size(); i++) {
            categories.add(categoryRepository.findById(request.getCategoriesId().get(i)).orElseThrow());
        }

        product.get().setName(request.getName());
        product.get().setPrice(request.getPrice());
        product.get().setQuantity(request.getQuantity());
        product.get().setUpdatedAt(new Timestamp(new Date(System.currentTimeMillis()).getTime()));
        product.get().setDescription(request.getDescription());
        product.get().setCategories(categories);
        product.get().setImageUrl(request.getImage_url());

        repository.save(product.get());

        applicationEventPublisher.publishEvent(new ProductInsertEvent(product.get()));

        return null;
    }

    @Override
    public String deleteProductById(String productId) {
        var product = repository.findById(productId);

        if (product.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Product Not Found");
        }

        repository.delete(product.get());
        applicationEventPublisher.publishEvent(new ProductInsertEvent(product.get()));
        return "Success Delete Product";
    }
}
