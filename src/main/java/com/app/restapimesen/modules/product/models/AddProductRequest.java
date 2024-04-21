package com.app.restapimesen.modules.product.models;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AddProductRequest {
    @NotBlank
    @NotNull
    private String name;

    private String description;

    @Valid
    private Long price;

    @Valid
    private Long quantity;

    private String image_url;

    @Valid
    private List<Long> categoriesId;
}
