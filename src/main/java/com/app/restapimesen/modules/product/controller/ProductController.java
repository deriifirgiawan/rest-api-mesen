package com.app.restapimesen.modules.product.controller;

import com.app.restapimesen.models.TypeOrder;
import com.app.restapimesen.models.WebResponse;
import com.app.restapimesen.modules.product.entity.ProductMv;
import com.app.restapimesen.modules.product.entity.Products;
import com.app.restapimesen.modules.product.libs.GenerateListProduct;
import com.app.restapimesen.modules.product.models.AddProductRequest;
import com.app.restapimesen.modules.product.models.RequestGetAllProduct;
import com.app.restapimesen.modules.product.models.TypePrice;
import com.app.restapimesen.modules.product.service.ProductService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Validator;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;


@Tag(name = "Products")
@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/api/v1/product")
public class ProductController {
    private final ProductService productService;
    private final Validator validator;
    private final GenerateListProduct generateListProduct;

    @GetMapping("/all/{storeId}")
    @ResponseStatus(HttpStatus.OK)
    public WebResponse<List<ProductMv>> getAllProduct(
            @NotNull
            @PathVariable(name = "storeId")
            String storeId,
            @RequestParam(name = "orderBy")
            Optional<TypeOrder> order,
            @RequestParam(name = "categories")
            Optional<String> categories,
            @RequestParam(name = "price")
            Optional<TypePrice> price
    ) {
        validator.validate(storeId);
        RequestGetAllProduct requestPayload = new RequestGetAllProduct();
        requestPayload.setOrder(order);
        requestPayload.setStoreId(storeId);
        requestPayload.setCategories(Optional.ofNullable(generateListProduct.generateCategoryToList(categories.get())));
        requestPayload.setPrice(price);

        var response = productService.getAllProductsByStoreId(requestPayload);
        return  WebResponse.<List<ProductMv>>builder()
                .status(HttpStatus.CREATED)
                .message("Success Get All Your Products")
                .data(response)
                .build();
    }

    @PostMapping(
            path = "/add"
    )
    @ResponseStatus(HttpStatus.CREATED)
    public WebResponse<Products> addProduct(
            @RequestBody AddProductRequest request,
            @NotNull @RequestParam("user_id") String userId

    ) {
        validator.validate(request);
        validator.validate(userId);

        var response = productService.addProduct(request, userId);

        return  WebResponse.<Products>builder()
                .status(HttpStatus.CREATED)
                .message("Success Add Product")
                .data(response)
                .build();
    }

    @PutMapping(
            path = "/update/{product_id}"
    )
    @ResponseStatus(HttpStatus.OK)
    public WebResponse<Products> updateProduct(
            @RequestBody AddProductRequest request,
            @NotNull @PathVariable("product_id") String product_id
    ) {
        validator.validate(request);
        validator.validate(product_id);

        var response = productService.updateProductById(request, product_id);

        return  WebResponse.<Products>builder()
                .status(HttpStatus.CREATED)
                .message("Success Updated Product")
                .data(response)
                .build();
    }

    @DeleteMapping(
            path = "/delete/{product_id}"
    )
    @ResponseStatus(HttpStatus.OK)
    public WebResponse<String> deleteProduct(
            @NotNull @RequestParam("product_id") String product_id

    ) {
        validator.validate(product_id);

        var response = productService.deleteProductById(product_id);

        return  WebResponse.<String>builder()
                .status(HttpStatus.CREATED)
                .message("Success Deleted Product")
                .data(response)
                .build();
    }
}
